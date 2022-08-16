package com.sipue.user.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class test {
    @XxlJob("demoJobHandler")
    public void demoJobHandler() {
        log.info("定时任务进行----------------------------------");
        XxlJobHelper.handleSuccess();
    }
}
