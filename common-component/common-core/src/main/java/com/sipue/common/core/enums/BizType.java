package com.sipue.common.core.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 业务类型
 * @Author mustang
 * @Date 2022年6月02日17:06:31
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum BizType {

    /**
     * 管理人端
     */
    GLHT("976cec48ac68a4504f2dee4322121b1b","管理后台"),
    ;

    private String code;
    private String name;

    /**
     * 根据业务类型代码获取业务类型
     * @param bizType
     * @return
     */
    public static BizType getBizTypeByCode(String bizType) {
        if(StrUtil.isEmpty(bizType))return null;
        bizType=bizType.toUpperCase();
        for (BizType enums : BizType.values()) {
            if (bizType.equals(enums.toString())) {
                return enums;
            }
        }
        return null;
    }
}
