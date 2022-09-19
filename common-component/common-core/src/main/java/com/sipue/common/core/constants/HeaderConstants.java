package com.sipue.common.core.constants;

/**
 * @Description 请求头静态常量
 * @Author wangjunyu
 * @Date 2022年6月25日11:00:50
 * @Version 1.0
 */
public interface HeaderConstants {

    String APP_ID = "appID";

    /**
     * 约定自定义请求头
     */
    String USERAGENT = "UserAgent";
    /**
     * 请求签名-签名值
     **/
    String SIGN = "sign";
    /**
     * 请求签名-时间戳
     **/
    String TIMESTAMP = "timeStamp";


    /**
     * 请求ID，用于记录日志跟踪
     **/
    String TRACE_ID = "traceId";


    /**
     * 请求Ip，用于记录日志跟踪
     **/
    String REQUEST_IP = "RequestIp";

    /**
     * http定义的用户代理
     **/
    String SWARTZ_USER_AGENT = "sipue-User-Agent";

    String CONTENT_TYPE = "Content-Type";

    /**
     * 当前用户信息(网关层会根据用户token放入请求头)
     **/
    String CURRENT_USER_INFO = "current-user-info";

    /**
     * 登录令牌信息
     */
    String TOKEN = "token";

    /**
     * 校验信息
     */
    String HTTP_SIGN = "httpSign";

    /**
     * timestamp
     */
    String TIME_STAMP = "timestamp";

}
