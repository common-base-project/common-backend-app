package top.mybi.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


/**
 * @Description 条数参数
 * @Author mustang
 * @Date 2022年6月28日10:55:05
 * @Version 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "mybi.common.request.log")
@Configuration
@Data
public class RequestLogConfig {

    /**
     * 请求日志打印：默认关闭
     */
    private boolean enabled = false;

    /**
     * 慢请求判断标准，默认为1s，响应超过1秒则记录为警告
     */
    private long slowRequestMillisecond = 1000;
}
