package top.mybi.common.redis.annotation;

import top.mybi.common.core.exception.ServiceException;
import top.mybi.common.core.model.CommonErrorCode;
import top.mybi.common.redis.constant.RedisKeyConst;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁面向切面实现
 * @version 1.0
 */
@Slf4j
@Aspect
public class RedissonLockAspect {

    private final RedissonClient redissonClient;

    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 参数分隔符
     */
    static String DELIMITER = ":";

    @Autowired
    public RedissonLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(top.mybi.common.redis.annotation.Lock)")
    public void execute() {
    }


    @Around("execute()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Lock lock = method.getAnnotation(Lock.class);
        String lockKey = getLockKey(lock, pjp);

        //加锁
        try {
            RLock rLock = redissonClient.getLock(lockKey);
            //获取开始时间
            long startTime = System.currentTimeMillis();
            boolean success = rLock.tryLock(lock.waite(), lock.expire(), TimeUnit.SECONDS);
            //获取结束时间
            long endTime = System.currentTimeMillis();
            log.debug("分布式锁开锁。名称：{},状态：{},耗时：{}毫秒", rLock.getName(), success ? "成功" : "失败", (endTime - startTime));
            if (success) {
                //加锁成功执行业务
                try {
                    return pjp.proceed();
                } catch (Throwable throwable) {
                    log.error("分布式锁捕获错误： {}", throwable.getMessage(), throwable);
                    if (throwable instanceof ServiceException) {
                        throw (ServiceException) throwable;
                    } else {
                        throw new ServiceException(CommonErrorCode.SERVER_ERROR);
                    }
                } finally {
                    log.debug("分布式锁解锁。名称：{}", rLock.getName());
                    //进允许持有锁的线程解锁 FuYouJ
                    if (rLock.isLocked() && rLock.isHeldByCurrentThread()){
                        rLock.unlock();
                    }else {
                        log.warn("请检查方法：{},锁名称：{}的相关代码，锁竞争激烈",method.getName(),rLock.getName());
                    }

                }
            } else {
                throw new ServiceException(CommonErrorCode.SERVER_LOCK_ERROR);
            }
        } catch (InterruptedException e) {
            throw new ServiceException(CommonErrorCode.SERVER_LOCK_ERROR);
        }


    }

    private String getLockKey(Lock lock, ProceedingJoinPoint pjp) {
        StringJoiner lockKey = new StringJoiner(DELIMITER);
        // caseId ,#params.caseId
        String[] lockParams = lock.value();
        lockKey.add(RedisKeyConst.LOCK_PREFIX);
        for (String lockParam : lockParams) {
            if (lockParam.startsWith("#")) {
                lockKey.add(explainSpel(lockParam, pjp));
            } else {
                lockKey.add(lockParam);
            }
        }
        //这个循环走下来 只要遵守规则 一定是前缀+caseId:xxxxxx(你希望的值)
        //swartz:poyitong:lock:caseId:1270182278168920065
        return lockKey.toString();
    }
    public String explainSpel(String spelStr, ProceedingJoinPoint joinPoint) {
        //方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //方法的参数名数组  比如 DTOA paramA , DTOB paramB   这是参数的名字
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        //spelstr: #param.caseId
        Expression expression = parser.parseExpression(spelStr);
        EvaluationContext context = new StandardEvaluationContext();
        //入参列表 具体到类型  名字和值 有几个参数长度就有几个 装的是具体的Type 所以根据param封装map
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            assert paramNames != null;
            //实际上是一个map
            context.setVariable(paramNames[i], args[i]);
        }
        //从参数里面 根据表达式拿到自己想要的值 #paramA.caseId
        Object requireNonNull = Objects.requireNonNull(expression.getValue(context));
        return requireNonNull.toString();
    }

}
