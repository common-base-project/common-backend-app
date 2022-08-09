package com.sipue.common.core.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.lang.annotation.*;

/**
 * 整合 @lazy和@Autowired(required=false)
 * @Author wangjunyu
 * @date
 * @version 1.0
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Lazy
@Autowired
public @interface LazyAutowired {

    boolean value() default true;

    boolean required() default true;
}
