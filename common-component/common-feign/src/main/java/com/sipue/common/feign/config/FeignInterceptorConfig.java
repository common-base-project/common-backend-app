package com.sipue.common.feign.config;

import cn.hutool.core.util.StrUtil;
import com.sipue.common.core.constants.CommonConstants;
import com.sipue.common.core.constants.HeaderConstants;
import com.sipue.common.core.constants.SecurityConstants;
import com.sipue.common.core.context.TenantContextHolder;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * feign拦截器，只包含http相关数据
 *
 */
@Slf4j
public class FeignInterceptorConfig {
    protected List<String> requestHeaders = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        requestHeaders.add(SecurityConstants.USER_ID_HEADER);//用户id信息
        requestHeaders.add(SecurityConstants.USER_HEADER);//用户信息
        requestHeaders.add(SecurityConstants.ROLE_HEADER);//角色信息
        requestHeaders.add(SecurityConstants.TENANT_HEADER);//租户请求信息
        requestHeaders.add(CommonConstants.sipue_VERSION);//负载均衡策略-版本号 信息头
        requestHeaders.add(HeaderConstants.CURRENT_USER_INFO);//当前用户信息
        requestHeaders.add(HeaderConstants.APP_ID.toLowerCase());//appid
    }

    /**
     * 使用feign client访问别的微服务时，将上游传过来的access_token、username、roles等信息放入header传递给下一个服务
     */
    @Bean
    public RequestInterceptor httpFeignInterceptor() {
        return template -> {
            //传递client
            String tenant = TenantContextHolder.getTenant();
            if (StrUtil.isNotEmpty(tenant)) {
                template.header(SecurityConstants.TENANT_HEADER, tenant);
            }
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    String headerName;
                    String headerValue;
                    while(headerNames.hasMoreElements()) {
                        headerName = headerNames.nextElement();
                        //if (requestHeaders.contains(headerName)) {
                            headerValue = request.getHeader(headerName);
                            template.header(headerName, headerValue);
                        //}
                    }
                }
                //传递access_token，无网络隔离时需要传递
                /*
                String token = extractHeaderToken(request);
                if (StrUtil.isEmpty(token)) {
                    token = request.getParameter(CommonConstant.ACCESS_TOKEN);
                }
                if (StrUtil.isNotEmpty(token)) {
                    template.header(CommonConstant.TOKEN_HEADER, CommonConstant.BEARER_TYPE + " " + token);
                }
                */
            }
        };
    }

    /**
     * 解析head中的token
     * @param request
     */
    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(CommonConstants.TOKEN_HEADER);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(CommonConstants.BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(CommonConstants.BEARER_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
