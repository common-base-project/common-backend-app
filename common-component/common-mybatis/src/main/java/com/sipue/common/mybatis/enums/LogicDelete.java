package com.sipue.common.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 逻辑删除标志
 * @Author wangjunyu
 * @Date 2022年6月22日18:11:06
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum LogicDelete {

    /**
     * 未删除
     */
    NORMAL(1),

    /**
     * 已删除
     */
    DELETE(2);

    private int value;
}
