package com.sipue.common.rabbitmq.properties;

import com.sipue.common.rabbitmq.config.RabbitModules;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;


/**
 * rabbitmq配置参数类
 * @Author wangjunyu
 * @date 2022年03月17日11:00:47
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = RabbitMqProperties.PREFIX)
public class RabbitMqProperties {
    public static final String PREFIX = "sipue.common.rabbitmq";

    /**
     * 组件信息(读取配置文件，自动创建队列信息)
     */
    private List<RabbitModules> modules;

}