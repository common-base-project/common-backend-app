package com.sipue.common.core.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * @Description 数字处理相关工具类
 * @Author mustang
 * @Date 2022年6月02日14:11:20
 * @Version 1.0
 */
public class NumberUtil {

    /**
     * 将Integer类型转换为int，null时返回0
     * @param num
     * @return
     */
    public static int nonNullNumber(Integer num) {
        return (num == null) ? 0 : num.intValue();
    }

    /**
     * 将一个Long类型转换为long，null时返回0
     * @param num
     * @return
     */
    public static long nonNullNumber(Long num) {
        return (num == null) ? 0L : num.longValue();
    }

    /**
     * 判断是否是正整数，大于0
     * @param num
     * @return 是正整数返回true，如果为null或者小于等于0返回false
     */
    public static boolean isPositiveNum(Long num) {
        return (num != null) && num.longValue() > 0;
    }

    /**
     * 判断是否是正整数，大于0
     * @param num
     * @return 是正整数返回true，如果为null或者小于等于0返回false
     */
    public static boolean isPositiveNum(Integer num) {
        return (num != null) && num.intValue() > 0;
    }

    /**
     * 获取雪花算法Id
     * @return
     */
    public static Long getSnowflake(){
        return IdWorker.getId();
    }
}
