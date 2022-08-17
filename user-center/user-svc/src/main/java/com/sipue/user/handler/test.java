package com.sipue.user.handler;

import com.sipue.common.core.constants.MessageQueueConst;
import com.sipue.common.rabbitmq.data.RabbitData;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class test {
    @XxlJob("demoJobHandler")
    public void demoJobHandler() {
        log.info("定时任务进行----------------------------------");
        XxlJobHelper.handleSuccess();
    }

    /**
     * 消息队列消费test
     * @param params
     */
    @RabbitHandler
    @RabbitListener(queues = MessageQueueConst.SMS_BACK_QUEUE)
    public void orderMqHandle(RabbitData params) {
        log.info("_-----------------开始消费");
    }
}
