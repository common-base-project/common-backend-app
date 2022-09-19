package com.sipue.backstage.pojo.dto.user;

import com.sipue.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 添加后台用户表请求参数
 *
 * @author mustang
 * @date 2022-07-11 17:05:16
 */
@ApiModel("添加后台用户表请求参数")
@Getter
@Setter
public class AddUserDTO extends BasePageDTO{

    @ApiModelProperty(value = "用户名",required = true)
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "手机号",required = true)
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty(value = "密码",required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "角色id",required = true)
    @NotEmpty(message = "角色id不能为空")
    private Long roleId;
}

