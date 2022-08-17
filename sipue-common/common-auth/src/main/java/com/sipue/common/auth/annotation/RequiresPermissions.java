package com.sipue.common.auth.annotation;

import com.sipue.common.auth.enums.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * @Description: 权限认证：必须具有指定权限才能进入该方法
 *
 * @Author: wangjunyu
 * @Date: 2022/8/17 14:09
 */
public @interface RequiresPermissions {
    /**
     * 需要校验的权限码
     */
    String[] value() default {};

    /**
     * 验证模式：AND | OR，默认AND
     */
    Logical logical() default Logical.AND;
}
