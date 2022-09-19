package com.sipue.common.core.logback;

import org.slf4j.MDC;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 上下文context准备时触发 将服务名称及服务环境放入MDC
 * @Author wangjunyu
 * @date
 * @version 1.0
 */
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationPreparedEvent>, Ordered {
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        ConfigurableApplicationContext context = applicationPreparedEvent.getApplicationContext();
        ConfigurableEnvironment environment = context.getEnvironment();
        String application = environment.getProperty("spring.application.name");
        String active = environment.getProperty("spring.profiles.active");
        MDC.put("application", application);
        MDC.put("environment", active);
    }
}
