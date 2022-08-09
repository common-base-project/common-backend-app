package com.sipue.common.core.constants;

/**
 * @Description 消息队列常量定义
 * @Author wangjunyu
 * @Date 2022年6月29日10:53:40
 * @Version 1.0
 */
public interface MessageQueueConst {
    /**
     * 短信消息回执(阿里云发送短信后更新发送状态用)
     */
    String SMS_BACK_EXCHANGE = "pyy.sms.back.exchange";
    String SMS_BACK_ROUTE = "pyy.sms.back.route";
    String SMS_BACK_QUEUE = "pyy.sms.back.queue";

    /**
     * 短信消息延迟发送
     */
    String SMS_DEAD_EXCHANGE = "pyy.sms.dead.exchange";
    String SMS_DEAD_ROUTE = "pyy.sms.dead.route";
    String SMS_DEAD_QUEUE = "pyy.sms.dead.queue";

    //短信消息
    String SMS_EXCHANGE = "pyy.sms.exchange";
    String SMS_ROUTE = "pyy.sms.route";
    String SMS_QUEUE = "pyy.sms.queue";
    //邮件消息
    String EMAIL_EXCHANGE = "pyy.email.exchange";
    String EMAIL_ROUTE = "pyy.email.route";
    String EMAIL_QUEUE = "pyy.email.queue";
    //系统消息
    String SYS_EXCHANGE = "pyy.sys.exchange";
    String SYS_ROUTE = "pyy.sys.route";
    String SYS_QUEUE = "pyy.sys.queue";
    //订单消息
    String ORDER_EXCHANGE = "pyy_order_exchange";
    String ORDER_ROUTE = "pyy_order_route";
    String ORDER_QUEUE = "pyy_order_queue";

    /**
     * 案源助手
     */
    String ASSISTANT_EXCHANGE = "assistant_exchange";
    String ASSISTANT_ROUTE = "assistant_route";
    String ASSISTANT_QUEUE = "assistant_queue";

    /**
     * 操作日志
     */
    String OPERATION_EXCHANGE = "operation_exchange";
    String OPERATION_ROUTE = "operation_route";
    String OPERATION_QUEUE = "operation_queue";

    /**
     * 案件动态
     */
    String CASE_DYNAMIC_EXCHANGE = "case_dynamic_exchange";
    String CASE_DYNAMIC_ROUTE = "case_dynamic_route";
    String CASE_DYNAMIC_QUEUE = "case_dynamic_queue";

    /**
     * 弓申报消费维权商家端已解决三天后需要自动确认
     */
    String CONSUMER_EXCHANGE = "gsb_consumer_exchange";
    String CONSUMER_ROUTE = "gsb_consumer_route";
    String CONSUMER_QUEUE = "gsb_consumer_queue";


}
