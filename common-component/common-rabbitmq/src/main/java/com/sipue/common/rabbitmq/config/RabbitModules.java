package com.sipue.common.rabbitmq.config;

/**
 * @Author mustang
 * @date 2022/3/17 1:41
 */

import lombok.Data;

/**
 * RabbitMq的队列，交互机，绑定关系的对象
 *
 */
@Data
public class RabbitModules {

    /**
     * 队列信息
     */
    private Queue queue;

    /**
     * 交换机信息
     */
    private Exchange exchange;

    /**
     * 交换机类型
     */
    public enum ExchangeType {
        /**
         * 自定义交换机
         */
        CUSTOM,
        /**
         * 直连交换机（全文匹配）
         */
        DIRECT,
        /**
         * 通配符交换机（两种通配符：*只能匹配一个单词，#可以匹配零个或多个）
         */
        TOPIC,
        /**
         * 头交换机（自定义键值对匹配，根据发送消息内容中的headers属性进行匹配）
         */
        HEADERS,
        /**
         * 扇形（广播）交换机 （将消息转发到所有与该交互机绑定的队列上）
         */
        FANOUT;

    }

    /**
     * 交换机的详细配置
     */
    @Data
    public static class Exchange {

        /**
         * 交互机名称
         */
        private String name;

        /**
         * 交互机类型。
         * 默认：直连型号
         */
        private ExchangeType type = ExchangeType.DIRECT;

        /**
         * 是否持久化
         * 默认true：当RabbitMq重启时，消息不丢失
         */
        private boolean durable = true;

        /**
         * 当所有绑定队列都不在使用时，是否自动 删除交换器
         * 默认值false：不自动删除，推荐使用。
         */
        private boolean autoDelete = false;

        /**
         * 判断是否是延迟交互机
         */
        private boolean delayed;
        /**
         * 自定义交换机类型
         */
        private String customType;

        /**
         * 交互机的额外参数
         */
        private java.util.Map<String, Object> arguments;

    }


    /**
     * 队列的详细信息
     * 提供默认的配置参数
     */
    @Data
    public static class Queue {

        /**
         * 队列名
         * 必填
         */
        private String name;

        /**
         * 路由主键
         */
        private String routingKey;

        /**
         * 是否持久化
         * 默认true：当RabbitMq重启时，消息不丢失
         */
        private boolean durable = true;

        /**
         * 是否具有排他性
         * 默认false：可以多个消息者消费同一个队列
         */
        private boolean exclusive = false;

        /**
         * 当消费者客户端均断开连接，是否自动删除队列
         * 默认值false：不自动删除，推荐使用，避免消费者断开后，队列中丢弃消息
         */
        private boolean autoDelete = false;

        /**
         * 队列的额外参数
         */
        private java.util.Map<String, Object> arguments;

    }


}