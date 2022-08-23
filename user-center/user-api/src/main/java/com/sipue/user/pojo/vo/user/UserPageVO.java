package com.sipue.user.pojo.vo.user;

import com.sipue.common.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

/**
 * 分页查询用户响应
 *
 * @author wangjunyu
 * @date 2022-08-22 17:05:34
 */
@ApiModel("分页查询用户响应")
@Getter
@Setter
public class UserPageVO extends BaseModel{

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

