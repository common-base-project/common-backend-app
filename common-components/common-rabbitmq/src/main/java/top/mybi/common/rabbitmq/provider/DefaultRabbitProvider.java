package top.mybi.common.rabbitmq.provider;

import com.alibaba.fastjson.JSON;
import top.mybi.common.rabbitmq.data.RabbitData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author mustang
 * @date 2022/2/7 15:40
 */
@Slf4j
public class DefaultRabbitProvider implements RabbitProvider {
    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 发送消息
     * @param exchange   交换机
     * @param routingKey 路由key
     * @param data       消息对象
     */
    @Override
    public void send(String exchange, String routingKey, RabbitData data) {
        send(exchange,routingKey,data,0);
    }

    /**
     * 发送消息
     * @param exchange 交换机
     * @param routingKey  路由key
     * @param data  消息对象
     * @param delay 延时时间消费 （单位毫秒）
     */
    @Override
    public void send(String exchange, String routingKey, RabbitData data, int delay) {
        String message="";
        try {
            message=JSON.toJSONString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("发送消息---->{}",message);
        if(delay<=0){
            rabbitTemplate.convertAndSend(exchange, routingKey, data);
        }else {
            rabbitTemplate.convertAndSend(exchange, routingKey, data, (d) -> {
                d.getMessageProperties().setDelay(delay);
                d.getMessageProperties().setExpiration(delay + "");
                return d;
            }, new CorrelationData(data.getUuid()));
        }
//        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
//            @Override
//            public void returnedMessage(ReturnedMessage returnedMessage) {
//                log.error("消息投递失败:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
//                        returnedMessage.getExchange(), returnedMessage.getRoutingKey(), returnedMessage.getReplyCode(), returnedMessage.getReplyText(), returnedMessage.getMessage());
//            }
//        });
    }
}