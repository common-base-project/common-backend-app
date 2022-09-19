package com.sipue.backstage.config;

import com.sipue.common.core.config.WebMvcConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author mustang
 */
@Configuration
@Import(value = {
        WebMvcConfig.class
})
@MapperScan("com.sipue.*.mapper")
@EnableFeignClients(basePackages = "com.sipue")
public class AppConfig {
}
