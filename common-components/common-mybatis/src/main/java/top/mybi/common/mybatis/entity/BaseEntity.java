package top.mybi.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import top.mybi.common.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * @Author mustang
 */
@Getter
@Setter
public class BaseEntity extends BaseModel {

    ///**
    // * 创建人员
    // */
    //@TableField(value = "creator",fill = FieldFill.INSERT)
    //private Long creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    ///**
    // * 最后一次修改人员
    // */
    //@TableField(value = "modifier",fill = FieldFill.INSERT_UPDATE)
    //private Long modifier;

    /**
     * 更新时间
     **/
    @TableField(value = "modify_time")
    private LocalDateTime modifyTime;


}