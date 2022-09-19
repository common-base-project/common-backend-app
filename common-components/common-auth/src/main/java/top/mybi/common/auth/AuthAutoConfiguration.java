package top.mybi.common.auth;

import top.mybi.common.auth.aop.RequiresAuthAspect;
import top.mybi.common.auth.properties.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @Author mustang
 * @date 2022/1/24 9:58
 */
@EnableConfigurationProperties({AuthProperties.class})
@Import(RequiresAuthAspect.class)
public class AuthAutoConfiguration {
}
