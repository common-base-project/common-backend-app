package com.sipue.common.core.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 平台信息
 * @Author wangjunyu
 * @Date 2022年6月28日10:26:48
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum Platform {

    /**
     * 通用
     */
    NORMAL(1),

    /**
     * WEB
     */
    WEB(2),

    /**
     * 小程序
     */
    APPLET(3),


    ;

    private int value;

    /**
     * 根据业务类型代码获取业务类型
     * @param platType
     * @return
     */
    public static Platform getPlatformByCode(String platType) {
        if(StrUtil.isEmpty(platType))return null;
        platType=platType.toUpperCase();
        for (Platform enums : Platform.values()) {
            if (platType.equals(enums.toString())) {
                return enums;
            }
        }
        return null;
    }
}
