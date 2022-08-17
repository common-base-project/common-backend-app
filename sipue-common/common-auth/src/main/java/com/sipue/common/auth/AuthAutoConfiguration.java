package com.sipue.common.auth;

import com.sipue.common.auth.aop.RequiresAuthAspect;
import com.sipue.common.auth.properties.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @Author wangjunyu
 * @date 2022/1/24 9:58
 */
@EnableConfigurationProperties({AuthProperties.class})
@Import(RequiresAuthAspect.class)
public class AuthAutoConfiguration {
}
