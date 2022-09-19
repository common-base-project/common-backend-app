package com.sipue.common.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author mustang
 * @date 2022/3/17 1:49
 */
@Slf4j
@Component
public class RabbitModulesRegister {

    @Resource
    private AmqpAdmin amqpAdmin;
    /**
     * 注册RabbitMq的组件信息
     *
     * @param queue    队列对象
     * @param exchange 交换机对象
     * @param binding  队列与交换机绑定关系对象
     */
    public void registerModule(Queue queue, Exchange exchange, Binding binding) {
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareBinding(binding);
        log.info("绑定队列({})到交换机({})-----success!",queue.getName(),exchange.getName());
    }
}
