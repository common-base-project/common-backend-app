package com.sipue.common.auth.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 认证服务端 属性
 *
 * @author wangjunyu
 * @date 2022/07/02
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = AuthProperties.PREFIX)
public class AuthProperties {
    public static final String PREFIX = "sipue.common.auth";

    /**
     * 过期时间 2h
     * 单位：s(秒)
     */
    private Long expire = 7200L;
    /**
     * 刷新token的过期时间 8h
     * 单位：s
     */
    private Long refreshExpire = 28800L;
    /**
     * 设置解析token时，允许的误差
     * 单位：s
     * 使用场景1：多台服务器集群部署时，服务器时间戳可能不一致
     * 使用场景2：？
     */
    private Long allowedClockSkewSeconds = 60L;

}
