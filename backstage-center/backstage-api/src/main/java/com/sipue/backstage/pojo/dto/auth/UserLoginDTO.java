package com.sipue.backstage.pojo.dto.auth;


import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录请求参数
 * @author 邓军
 * @date 2020年01月06日14:42:59
 * @version 1.0
 */
@ApiModel(value = "用户登录请求参数")
@Setter
@Getter
public class UserLoginDTO extends BaseModel {

    @ApiModelProperty(value = "手机号码",required = true)
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
}
