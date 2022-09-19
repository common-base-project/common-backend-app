package top.mybi.common.auth.enums;

/**
 * @Description: 权限注解的验证模式
 *
 * @Author: mustang
 * @Date: 2022/8/17 14:07
 */
public enum Logical {
    /**
     * 必须具有所有的元素
     */
    AND,

    /**
     * 只需具有其中一个元素
     */
    OR
}
