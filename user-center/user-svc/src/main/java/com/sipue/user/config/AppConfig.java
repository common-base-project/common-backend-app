package com.sipue.user.config;

import com.sipue.backstage.config.BackstageSvcFeignConfig;
import com.sipue.backstage.service.BackstageUserFeignService;
import com.sipue.common.core.config.WebMvcConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author wangjunyu
 */
@Configuration
@Import(value = {
        WebMvcConfig.class,
        BackstageSvcFeignConfig.class
})
@MapperScan("com.sipue.*.mapper")
@EnableFeignClients(basePackages = "com.sipue")
public class AppConfig {

}
