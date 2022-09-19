package com.sipue.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.sipue.common.auth.model.AuthAccessToken;
import com.sipue.common.auth.store.TokenStore;
import com.sipue.common.core.constants.HeaderConstants;
import com.sipue.common.core.exception.ServiceException;
import com.sipue.common.core.model.ClientInfo;
import com.sipue.common.core.model.CommonErrorCode;
import com.sipue.gateway.config.GatewayConfigProperties;
import com.sipue.gateway.utils.IgnoreUrlCheckUtil;
import com.sipue.gateway.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author mustang
 * @date 2022/1/24 15:03
 */
@Slf4j
@RefreshScope
@Component
public class HttpFilter extends BaseFilter {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private GatewayConfigProperties gatewayConfigProperties;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getURI().getPath();
        Object requestBody = exchange.getAttribute(POST_REQUEST_BODY);
        //无需验证url,比如swagger
        if(!CollectionUtils.isEmpty(gatewayConfigProperties.getIgnoreUrls())&& IgnoreUrlCheckUtil.check(gatewayConfigProperties.getIgnoreUrls(),requestUri)){
            return chain.filter(exchange);
        }

        String httpSign = request.getHeaders().getFirst(HeaderConstants.HTTP_SIGN);
        //验证请求客户端是的正确
        String appId = request.getHeaders().getFirst(HeaderConstants.APP_ID);
        ClientInfo client;
        try {
            client = new ClientInfo(appId);
        } catch (ServiceException e) {
            return response(exchange, e);
        }
        boolean checkSign=gatewayConfigProperties.isCheckSign();
        List<String> ignoreSignUrls=gatewayConfigProperties.getIgnoreSignUrls();
        //参数签名校验
        if(checkSign) {
            boolean isCheck = true;
            //是否忽略url
            if(!CollectionUtils.isEmpty(ignoreSignUrls)&& IgnoreUrlCheckUtil.check(ignoreSignUrls,requestUri)){
                isCheck=false;
            }
            if(isCheck) {
                String token = request.getHeaders().getFirst(HeaderConstants.TOKEN);
                AuthAccessToken authAccessToken = tokenStore.readAccessToken(client.getBizType(),client.getPlatform(),token);
                Long userId = authAccessToken == null ? null : authAccessToken.getUserId();
                String md5Data = Md5Util.getHttpSignMd5(request, requestBody, client, userId);
                String serverHttpSign = SecureUtil.md5(md5Data);
                if (!serverHttpSign.equals(httpSign)) {
                    log.error("参数签名校验失败!加密前:{};加密后--->前端:{};后端:{}", md5Data, httpSign, serverHttpSign);
                    return response(exchange, CommonErrorCode.HTTP_SIGN_FAILED);
                }
            }
        }
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return HTTP_SIGN_FILTER_ORDER;
    }
}
