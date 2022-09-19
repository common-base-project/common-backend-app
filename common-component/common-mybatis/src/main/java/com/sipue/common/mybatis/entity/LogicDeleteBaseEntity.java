package com.sipue.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 逻辑删除基础实体
 * @Author mustang
 * @Date 2022年6月27日18:26:26
 * @Version 1.0
 */
@Setter
@Getter
public class LogicDeleteBaseEntity extends BaseEntity {

    /**
     * 是否已删除（1：未删除，2：已删除）
     */
    @TableLogic(value = "1",delval = "2")
    @TableField(value = "deleted",fill = FieldFill.INSERT)
    private Integer deleted;

}
