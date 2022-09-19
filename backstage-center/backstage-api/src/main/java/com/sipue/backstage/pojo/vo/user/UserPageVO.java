package com.sipue.backstage.pojo.vo.user;

import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 分页查询用户表响应
 *
 * @author mustang
 * @date 2022-07-11 17:05:16
 */
@ApiModel("分页查询用户表响应")
@Getter
@Setter
public class UserPageVO extends BaseModel {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("用户状态：1：正常，2：停用，3：锁定")
    private Integer status;

    @ApiModelProperty("上次登录时间")
    private LocalDateTime lastLoginTime;

}

