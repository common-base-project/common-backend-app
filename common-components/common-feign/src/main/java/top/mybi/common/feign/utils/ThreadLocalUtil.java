package top.mybi.common.feign.utils;

import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Desc
 * @Author mustang
 * @date 2022/4/6 10:36
 */
public class ThreadLocalUtil {
    public  static ThreadLocal<ServletRequestAttributes> attributesThreadLocal = new InheritableThreadLocal<>();
}