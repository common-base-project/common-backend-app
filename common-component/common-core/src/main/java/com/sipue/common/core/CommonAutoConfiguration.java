package com.sipue.common.core;



import com.sipue.common.core.aop.RequestLogAspect;
import com.sipue.common.core.config.RequestLogConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自动装载全局配置
 * @Author mustang
 * @date 2022/12/15 14:04
 */

@Import({RequestLogConfig.class, RequestLogAspect.class})
public class CommonAutoConfiguration {
    @Bean
    public RequestLogConfig requestLogConfig(){
        return new RequestLogConfig();
    }
    //@Bean
    //public DefaultPasswordConfig defaultPasswordConfig() {
    //    return new DefaultPasswordConfig();
    //}
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return new BCryptPasswordEncoder();
    }
}
