package top.mybi.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import top.mybi.common.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 包含人员业务类型（来源）的基础实体
 * @Author mustang
 * @date 2022年6月17日10:10:50
 * @version 1.0
 */
@Getter
@Setter
public class DateTimeBaseEntity extends BaseModel {

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     **/
    @TableField(value = "modify_time")
    private LocalDateTime modifyTime;
}
