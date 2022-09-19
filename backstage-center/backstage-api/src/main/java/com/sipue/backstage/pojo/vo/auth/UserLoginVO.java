package com.sipue.backstage.pojo.vo.auth;


import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Description: 登录响应用户
 *
 * @Author: mustang
 * @Date: 2022/7/11 18:22
 */
@ApiModel(value = "登录响应用户")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO extends BaseModel {

    /**
     * 令牌信息
     */
    @ApiModelProperty(value = "令牌信息")
    private TokenVO tokenInfo;

    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    private LoginUserVO userInfo;
}
