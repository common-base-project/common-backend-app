package top.mybi.common.auth.model;

import top.mybi.common.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Description 登录令牌信息
 * @Author mustang
 * @Date 2022年6月28日10:46:381
 * @Version 1.0
 */
@Setter
@Getter
public class AuthAccessToken extends BaseModel {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 平台
     */
    private String platform;

    /**
     * 业务系统标识
     */
    private String bizType;
    /**
     * 令牌
     */
    private String token;
    /**
     * 令牌类型
     */
    private String tokenType;
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
