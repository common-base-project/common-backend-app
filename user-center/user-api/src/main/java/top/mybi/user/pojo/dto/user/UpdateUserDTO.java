package top.mybi.user.pojo.dto.user;

import top.mybi.common.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;


/**
 * 修改用户请求参数
 *
 * @author mustang
 * @date 2022-08-22 16:55:22
 */
@ApiModel("修改用户请求参数")
@Getter
@Setter
public class UpdateUserDTO extends BaseModel{

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("用户状态：1：正常，2：停用")
    private Integer status;

    @ApiModelProperty("上次登录时间")
    private LocalDateTime lastLoginTime;
}

