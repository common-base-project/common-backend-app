package top.mybi.common.core.aop;

import cn.hutool.core.util.StrUtil;
import top.mybi.common.core.config.RequestLogConfig;
import top.mybi.common.core.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description 请求参数日志打印拦截器
 * @Author mustang
 * @Date 2022年6月25日10:49:37
 * @Version 1.0
 */
@Slf4j
@Aspect
public class RequestLogAspect {

    /**
     * 记录请求的开始时间，放到request请求做为key
     */
    private static final String REQUEST_BEGIN_TIME_KEY = RequestLogAspect.class.getName() + "#request_begin_time";

    /**
     * 调试配置
     */
    @Resource
    private RequestLogConfig requestLogConfig;


    public RequestLogAspect() {

    }

    /**
     * 拦截所有控制器的方法
     */
    @Pointcut("@within(org.springframework.stereotype.Controller) || " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        if (!requestLogConfig.isEnabled()) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(Objects.isNull(attributes))return;
        HttpServletRequest request = attributes.getRequest();
        request.setAttribute(REQUEST_BEGIN_TIME_KEY, System.currentTimeMillis());

        StringBuilder requestLog = new StringBuilder();
        requestLog.append("【请求参数】");
        String appID = request.getHeader(CommonConstants.APP_ID);
        if (!StrUtil.isEmpty(appID)) {
            requestLog.append("【").append(appID).append("】");
        }
        requestLog.append("【").append(request.getMethod()).append("】");
        //类的名称
        requestLog.append(joinPoint.getTarget().getClass().getSimpleName()).append("#");
        //方法的名称
        Signature signature = joinPoint.getSignature();
        requestLog.append(signature.getName()).append("(");
        //方法参数
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                String str = (args[i] == null) ? "" : args[i].toString();
                if (i == 0) {
                    requestLog.append(str);
                } else {
                    requestLog.append(", ").append(str);
                }
            }
        }
        requestLog.append(")");
        log.info(requestLog.toString());

    }

    @AfterReturning(returning = "result", pointcut = "pointcut()")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {

        if (!requestLogConfig.isEnabled()) {
            return;
        }
        //判断是否记录响应数据日志
        Signature signature = joinPoint.getSignature();
        StringBuilder responseLog = new StringBuilder();
        responseLog.append("【响应数据】");
        //类的名称
        responseLog.append(joinPoint.getTarget().getClass().getSimpleName()).append("#");
        //方法的名称
        responseLog.append(signature.getName()).append("(");
        responseLog.append(result == null ? "" : (result.toString().length()>1000?(result.toString().substring(0,1000)+"...略去超过1000的数据"):result.toString()));
        responseLog.append(")");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(Objects.isNull(attributes))return;
        Object requestBeginTime = attributes.getRequest().getAttribute(REQUEST_BEGIN_TIME_KEY);
        long dealTime = System.currentTimeMillis() - Long.parseLong(requestBeginTime == null ? "0" : requestBeginTime.toString());
        responseLog.append(" 请求耗时【").append(dealTime).append("】毫秒");
        if (dealTime > requestLogConfig.getSlowRequestMillisecond()) {
            //将处理时间超过3秒的记录警告日志
            log.warn(responseLog.toString());
        } else {
            log.info(responseLog.toString());
        }
    }

}
