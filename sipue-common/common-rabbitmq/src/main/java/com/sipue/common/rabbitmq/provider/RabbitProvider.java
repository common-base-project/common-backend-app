package com.sipue.common.rabbitmq.provider;

import com.sipue.common.rabbitmq.data.RabbitData;

public interface RabbitProvider {
    /**
     *
     * @param exchange 交换机
     * @param routingKey  路由key
     * @param data  消息对象
     */
    void send(String exchange, String routingKey, RabbitData data);
    /**
     *
     * @param exchange 交换机
     * @param routingKey  路由key
     * @param data  消息对象
     * @param delay 延时时间消费 （单位毫秒）
     */
    void send(String exchange, String routingKey, RabbitData data, int delay);
}
