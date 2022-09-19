package com.sipue.common.rabbitmq.config;

import com.sipue.common.rabbitmq.properties.RabbitMqProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author mustang
 * @date 2022/3/17 1:50
 */
@Slf4j
@Component
public class RabbitModulesInit implements SmartInitializingSingleton {

    private RabbitModulesRegister rabbitModulesRegister;
    private RabbitMqProperties rabbitMqProperties;


    public RabbitModulesInit(RabbitModulesRegister rabbitModulesRegister, RabbitMqProperties rabbitMqProperties) {
        this.rabbitModulesRegister=rabbitModulesRegister;
        this.rabbitMqProperties = rabbitMqProperties;
    }

    @SneakyThrows
    @Override
    public void afterSingletonsInstantiated() {
        log.info("动态创建MQ配置信息...");
        declareModule();
    }

    private void declareModule() throws Exception {
        //获取组件信息
        List<RabbitModules> moduleInfos = rabbitMqProperties.getModules();
        if (CollectionUtils.isEmpty(moduleInfos)) {
            log.warn("配置文件中不含有组件信息，不进行注册！");
            return;
        }
        //注册组件信息
        for (RabbitModules moduleInfo : moduleInfos) {
            //数据校验
            declareValidate(moduleInfo);
            //获取队列
            Queue queue = tranSealQueue(moduleInfo.getQueue());
            //获取交换机
            Exchange exchange = tranSealExchange(moduleInfo.getExchange());
            //绑定关系
            Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE,
                    exchange.getName(), moduleInfo.getQueue().getRoutingKey(), null);

            //创建队列
//            amqpAdmin.declareQueue(queue);
//            amqpAdmin.declareExchange(exchange);
//            amqpAdmin.declareBinding(binding);
            rabbitModulesRegister.registerModule(queue,exchange,binding);
        }
    }


    /**
     * 声明模块数据校验
     *
     * @param moduleInfo 配置文件的模块信息
     */
    public void declareValidate(RabbitModules moduleInfo) throws Exception {
        //判断关键参数是否存在且合法

        if (moduleInfo.getQueue() == null) {
            throw new Exception(String.format("rabbitmq配置文件中，未配置queue！"));
        }
        if (moduleInfo.getQueue().getName() == null) {
            throw new Exception(String.format("rabbitmq配置文件中，未配置queue的name属性！"));
        }
        if (moduleInfo.getQueue().getRoutingKey() == null) {
            throw new Exception(String.format("rabbitmq配置文件中，未配置routingKey属性！"));
        }
        if (moduleInfo.getExchange() == null) {
            throw new Exception(String.format("rabbitmq配置文件中，未配置exchange！"));
        }
        if (moduleInfo.getExchange().getName() == null) {
            throw new Exception(String.format("rabbitmq配置文件中，未配置exchange的name属性！"));
        }


    }


    /**
     * 队列的对象转换
     *
     * @param queue 自定义的队列信息
     * @return RabbitMq的Queue对象
     */
    private Queue tranSealQueue(RabbitModules.Queue queue) {
        Map<String, Object> arguments = queue.getArguments();
//        //判断是否需要绑定死信队列
//        if (queue.getDeadExchangeName() != null && queue.getDeadRoutingKey() != null) {
////            //设置响应参数
////            if (queue.getArguments() == null) {
////                arguments = new HashMap<>(2);
////            }
////            arguments.put(DEAD_LETTER_QUEUE_KEY, queue.getDeadExchangeName());
////            arguments.put(DEAD_LETTER_ROUTING_KEY, queue.getDeadRoutingKey());
//
//            //只需要在声明业务队列时添加x-dead-letter-exchange，值为死信交换机
//            Map<String,Object> map = new HashMap<>(2);
//            map.put("x-dead-letter-exchange",queue.getDeadExchangeName());
//            //该参数x-dead-letter-routing-key可以修改该死信的路由key，不设置则使用原消息的路由key
//            map.put("x-dead-letter-routing-key",queue.getDeadRoutingKey());
//
//            return new Queue(queue.getName(),true,false,false,map);
//        }else{
//            return new Queue(queue.getName(), queue.isDurable(), queue.isExclusive(), queue.isAutoDelete(), arguments);
//        }
        return new Queue(queue.getName(), queue.isDurable(), queue.isExclusive(), queue.isAutoDelete(), arguments);

    }


    /**
     * 将配置信息转换交换机信息
     *
     * @param exchangeInfo 自定义交换机信息
     * @return RabbitMq的Exchange的信息
     */
    private Exchange tranSealExchange(RabbitModules.Exchange exchangeInfo) {
        AbstractExchange exchange = null;
        //判断类型
        switch (exchangeInfo.getType()) {
            //直连模式
            case DIRECT:
                exchange = new DirectExchange(exchangeInfo.getName(), exchangeInfo.isDurable(), exchangeInfo.isAutoDelete(), exchangeInfo.getArguments());
                break;
            //广播模式：
            case FANOUT:
                exchange = new FanoutExchange(exchangeInfo.getName(), exchangeInfo.isDurable(), exchangeInfo.isAutoDelete(), exchangeInfo.getArguments());
                break;
            //通配符模式
            case TOPIC:
                exchange = new TopicExchange(exchangeInfo.getName(), exchangeInfo.isDurable(), exchangeInfo.isAutoDelete(), exchangeInfo.getArguments());
                break;
            case HEADERS:
                exchange = new HeadersExchange(exchangeInfo.getName(), exchangeInfo.isDurable(), exchangeInfo.isAutoDelete(), exchangeInfo.getArguments());
                break;
            //自定义模式
            case CUSTOM:
                exchange = new CustomExchange(exchangeInfo.getName(), exchangeInfo.getCustomType(), exchangeInfo.isDurable(), exchangeInfo.isAutoDelete(), exchangeInfo.getArguments());
        }
        //设置延迟队列
        //exchange.setDelayed(exchangeInfo.isDelayed());
        //log.info(exchangeInfo.getName()+":"+exchangeInfo.isDelayed());
        return exchange;
    }

}
