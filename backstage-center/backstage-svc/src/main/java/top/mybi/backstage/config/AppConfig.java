package top.mybi.backstage.config;

import top.mybi.common.core.config.WebMvcConfig;
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
@MapperScan("top.mybi.*.mapper")
@EnableFeignClients(basePackages = "top.mybi")
public class AppConfig {
}
