package top.mybi.backstage.config;

import top.mybi.backstage.service.BackstageUserFeignService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 服务远程配置
 *
 * @Author: mustang
 * @Date: 2022/7/22 18:02
 */
@Configuration
public class BackstageSvcFeignConfig {

    @Bean
    public BackstageUserFeignService backstageFeignService(){
        return new BackstageUserFeignService();
    }

}
