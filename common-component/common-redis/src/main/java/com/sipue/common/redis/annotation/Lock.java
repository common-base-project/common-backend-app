package com.sipue.common.redis.annotation;

import java.lang.annotation.*;

/**
 *
 * @Author mustang
 * @date
 * @version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Lock {

    /**
     * 锁的名字，采用数组的形式拼接字符串作为锁的key
     *
     * 支持Spring Expression Language (SpEL) expression
     */
    String[] value();

    /**
     * 过期秒数,默认为5秒
     *
     * @return 轮询锁的时间
     */
    int expire() default 5;

    /**
     * 等待获取锁时间，默认位1秒
     * @return
     */
    int waite() default 1;

}
