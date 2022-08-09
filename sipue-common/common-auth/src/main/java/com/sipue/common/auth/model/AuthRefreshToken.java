package com.sipue.common.auth.model;

import com.sipue.common.core.model.BaseModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 刷新令牌信息
 * @Author wangjunyu
 * @date 2022/1/24 10:32
 */
@Data
public class AuthRefreshToken extends BaseModel {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 过期时间(秒)
     */
    private long expire;
    /**
     * 过期时间(日期格式)
     */
    private LocalDateTime expireTime;

}
