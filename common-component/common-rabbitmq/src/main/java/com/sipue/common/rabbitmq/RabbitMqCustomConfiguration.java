package com.sipue.common.rabbitmq;

import com.sipue.common.rabbitmq.config.RabbitModulesInit;
import com.sipue.common.rabbitmq.config.RabbitModulesRegister;
import com.sipue.common.rabbitmq.properties.RabbitMqProperties;
import com.sipue.common.rabbitmq.provider.DefaultRabbitProvider;
import com.sipue.common.rabbitmq.provider.RabbitProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbit 配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitMqCustomConfiguration {
    @Bean
    @ConditionalOnMissingBean(RabbitProvider.class)
    public DefaultRabbitProvider defaultRabbitProvider(){
        return new DefaultRabbitProvider();
    }
    @Bean
    @ConditionalOnMissingBean
    public RabbitModulesInit declareRabbitModule(RabbitModulesRegister rabbitModulesRegister, RabbitMqProperties rabbitMqProperties) {
        return new RabbitModulesInit(rabbitModulesRegister,rabbitMqProperties);
    }

}
