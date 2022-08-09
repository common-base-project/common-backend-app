package com.sipue.backstage.pojo.vo.auth;


import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 令牌信息
 * @author 邓军
 * @date 2020年01月06日14:25:45
 * @version 1.0
 */
@ApiModel(value = "令牌信息")
@Setter
@Getter
public class TokenVO extends BaseModel {

    /**
     * 登录令牌
     */
    @ApiModelProperty(value = "登录令牌")
    private String token;

    /**
     * 剩余失效时间（秒）
     */
    @ApiModelProperty(value = "剩余失效时间（秒）")
    private long expireTime;

    /**
     * 刷新令牌
     */
    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;
}
