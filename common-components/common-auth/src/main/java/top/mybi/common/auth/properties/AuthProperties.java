package top.mybi.common.auth.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 认证服务端 属性
 *
 * @author mustang
 * @date 2022/07/02
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = AuthProperties.PREFIX)
public class AuthProperties {
    public static final String PREFIX = "mybi.common.auth";

    /**
     * 过期时间 24h
     * 单位：s(秒)
     */
    private Long expire = 60 * 60 * 24L;
    /**
     * 刷新token的过期时间 30天
     * 单位：s
     */
    private Long refreshExpire =  60 * 60 * 24 * 30L;;
    /**
     * 设置解析token时，允许的误差
     * 单位：s
     * 使用场景1：多台服务器集群部署时，服务器时间戳可能不一致
     * 使用场景2：？
     */
    private Long allowedClockSkewSeconds = 60L;

}
