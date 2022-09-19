package com.sipue.common.auth.aop;

import com.sipue.common.auth.annotation.RequiresPermissions;
import com.sipue.common.auth.enums.Logical;
import com.sipue.common.core.exception.ServiceException;
import com.sipue.common.core.model.CommonErrorCode;
import com.sipue.common.core.model.Session;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 权限认证
 *
 * @Author: mustang
 * @Date: 2022/8/17 14:19
 */
@Slf4j
@Aspect
public class RequiresAuthAspect {

    public RequiresAuthAspect() {

    }

    /**
     * 拦截所有控制器的方法
     */
    @Pointcut("@annotation(com.sipue.common.auth.annotation.RequiresPermissions)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取注解Action
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions.logical() == Logical.AND) {
            checkPermiAnd(requiresPermissions.value());
        } else {
            checkPermiOr(requiresPermissions.value());
        }
    }

    /**
     * 验证用户是否含有指定权限，必须全部拥有
     *
     * @param permissions 权限列表
     */
    public void checkPermiAnd(String... permissions) {
        List<String> permissionList = Session.getUser().getPerissions();
        for (String permission : permissions) {
            if (!hasPermi(permissionList, permission)) {
                throw new ServiceException(CommonErrorCode.AUTH_NOT_ACCESS);
            }
        }
    }

    /**
     * 验证用户是否含有指定权限，只需包含其中一个
     *
     * @param permissions 权限码数组
     */
    public void checkPermiOr(String... permissions) {
        List<String> permissionList = Session.getUser().getPerissions();
        for (String permission : permissions) {
            if (hasPermi(permissionList, permission)) {
                return;
            }
        }
        if (permissions.length > 0) {
            throw new ServiceException(CommonErrorCode.AUTH_NOT_ACCESS);
        }
    }

    /**
     * 判断是否包含权限
     *
     * @param authorities 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(Collection<String> authorities, String permission) {
        return authorities.stream().filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(x, permission));
    }

}
