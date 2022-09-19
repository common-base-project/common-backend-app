package com.sipue.user.pojo.dto.user;

import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * 用户id请求参数
 *
 * @author mustang
 * @date 2022-08-22 16:55:22
 */
@ApiModel("用户id请求参数")
@Getter
@Setter
public class UserIdDTO extends BaseModel{

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;
}

