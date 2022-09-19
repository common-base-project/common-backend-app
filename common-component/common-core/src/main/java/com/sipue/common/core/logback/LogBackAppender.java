package com.sipue.common.core.logback;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.logback.LoghubAppender;
import com.aliyun.openservices.log.logback.LoghubAppenderCallback;
import com.aliyun.openservices.log.producer.LogProducer;
import com.sipue.common.core.constants.HeaderConstants;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LogBackAppender<E> extends LoghubAppender<E> {

    protected String timeFormat = "yyyy-MM-dd HH:mm:ss.SSS";
    protected DateTimeFormatter formatter;


    @Override
    public void start() {
        try {
            doStart();
        } catch (Exception e) {
            addError("Failed to start LoghubAppender.", e);
        }
    }

    private void doStart() {
        formatter = DateTimeFormat.forPattern(timeFormat);
        producerConfig.userAgent = "logback";
        producer = new LogProducer(producerConfig);
        producer.setProjectConfig(projectConfig);

        super.start();
    }

    @Override
    public void append(E eventObject) {
        try {
            appendEvent(eventObject);
        } catch (Exception e) {
            addError("Failed to append event.", e);
        }
    }

    private void appendEvent(E eventObject) {
        //init Event Object
        if (!(eventObject instanceof LoggingEvent)) {
            return;
        }
        LoggingEvent event = (LoggingEvent) eventObject;

        List<LogItem> logItems = new ArrayList<LogItem>();
        LogItem item = new LogItem();
        logItems.add(item);
        item.SetTime((int) (event.getTimeStamp() / 1000));

        DateTime dateTime = new DateTime(event.getTimeStamp());
        item.PushBack("time", dateTime.toString(formatter));
        item.PushBack("level", event.getLevel().toString());
        item.PushBack("thread", event.getThreadName());
        String traceId = MDC.get(HeaderConstants.TRACE_ID);
        if (!StringUtils.isEmpty(traceId)) {
            item.PushBack("traceId", traceId.substring(1, traceId.length() - 1));
        }

        String application = MDC.get("application");
        if (!StringUtils.isEmpty(application)){
            item.PushBack("application",application);
        }
        String environment = MDC.get("environment");
        if (!StringUtils.isEmpty(environment)){
            item.PushBack("environment",environment);
        }


        StackTraceElement[] caller = event.getCallerData();
        if (caller != null && caller.length > 0) {
            item.PushBack("location", caller[0].toString());
        }

        String message = event.getFormattedMessage();
        item.PushBack("message", message);

        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy != null) {
            String throwable = getExceptionInfo(iThrowableProxy);
            throwable += fullDump(event.getThrowableProxy().getStackTraceElementProxyArray());
            item.PushBack("throwable", throwable);
        }

        if (this.encoder != null) {
            item.PushBack("log", new String(this.encoder.encode(eventObject)));
        }

        producer.send(projectConfig.projectName, logstore, topic, source, logItems, new LoghubAppenderCallback<E>(this,
                projectConfig.projectName, logstore, topic, source, logItems));
    }

    private String getExceptionInfo(IThrowableProxy iThrowableProxy) {
        String s = iThrowableProxy.getClassName();
        String message = iThrowableProxy.getMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

    private String fullDump(StackTraceElementProxy[] stackTraceElementProxyArray) {
        StringBuilder builder = new StringBuilder();
        for (StackTraceElementProxy step : stackTraceElementProxyArray) {
            builder.append(CoreConstants.LINE_SEPARATOR);
            String string = step.toString();
            builder.append(CoreConstants.TAB).append(string);
            ThrowableProxyUtil.subjoinPackagingData(builder, step);
        }
        return builder.toString();
    }

}
