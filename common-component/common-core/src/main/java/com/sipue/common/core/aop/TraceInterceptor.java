package com.sipue.common.core.aop;

import com.sipue.common.core.constants.HeaderConstants;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TraceInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求Id放入MDC，可以在logback使用
        MDC.put(HeaderConstants.TRACE_ID,request.getHeader(HeaderConstants.TRACE_ID));
        return true;
    }

}

