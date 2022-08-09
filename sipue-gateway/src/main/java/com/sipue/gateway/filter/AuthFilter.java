package com.sipue.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.sipue.common.auth.model.AuthAccessToken;
import com.sipue.common.auth.model.Authority;
import com.sipue.common.auth.store.TokenStore;
import com.sipue.common.core.constants.GlobalConstants;
import com.sipue.common.core.constants.HeaderConstants;
import com.sipue.common.core.constants.RedisConstants;
import com.sipue.common.core.enums.BizType;
import com.sipue.common.core.enums.Platform;
import com.sipue.common.core.model.CommonErrorCode;
import com.sipue.common.core.model.Session;
import com.sipue.common.core.model.UserDetail;
import com.sipue.common.core.utils.DesUtil;
import com.sipue.common.core.utils.IpUtils;
import com.sipue.common.redis.template.RedisRepository;
import com.sipue.gateway.config.GatewayConfigProperties;
import com.sipue.gateway.utils.IgnoreUrlCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 权限拦截器
 * @Author wangjunyu
 * @date 2022/1/25 18:17
 */
@Slf4j
@Component
public class AuthFilter extends BaseFilter{
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private GatewayConfigProperties gatewayConfigProperties;

    static String API_DEFINE_LIST= RedisConstants.GATEWAY_CENTER_KEY+"api-define-list";

    /**
     * Process the Web request and (optionally) delegate to the next {@code WebFilter}
     * through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String requestUri = request.getURI().getPath();

        //无需验证url,比如swagger
        if(!CollectionUtils.isEmpty(gatewayConfigProperties.getIgnoreUrls())&& IgnoreUrlCheckUtil.check(gatewayConfigProperties.getIgnoreUrls(),requestUri)){
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst(HeaderConstants.TOKEN);
        if (StrUtil.isEmpty(token)) {
            return response(exchange, CommonErrorCode.ACCESS_TOKEN_EMPTY);
        }
        BizType bizType=Session.getBizType(request);
        Platform platform=Session.getPlatform(request);
        AuthAccessToken authAccessToken = tokenStore.readAccessToken(bizType,platform,token);
        if (Objects.isNull(authAccessToken)) {
            //重复登录提醒
            if (tokenStore.checkRepeatLogin(bizType,platform,token)) {
                return response(exchange, CommonErrorCode.ACCOUNT_LOGIN_IN_OTHER_REGION);
            }
            //强制下线登录提醒
            if (tokenStore.checkForceLogout(token)) {
                return response(exchange, CommonErrorCode.ACCOUNT_FORCE_LOGOUT);
            }
            return response(exchange, CommonErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        Authority authority = tokenStore.readAuthority(bizType, platform, authAccessToken.getUserId());
        if (Objects.isNull(authority)) {
            return response(exchange, CommonErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        //登录检查通过，将登录的用户信息放入请请求头
        UserDetail userDetail = authority.getUserDetail();
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            try {
                if(!StrUtil.isEmpty(userDetail.getNickName())) {
                    userDetail.setNickName(URLEncoder.encode(userDetail.getNickName(), GlobalConstants.CHARSET));
                }
                httpHeader.set(HeaderConstants.CURRENT_USER_INFO, JSONObject.toJSONString(userDetail));
                httpHeader.set(HeaderConstants.REQUEST_IP, IpUtils.getIpAddress(request));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        };
        /*if (!checkAuth(bizType,userDetail,apiDefine)){
            log.debug("用户{}({})无接口调用权限:{}",userDetail.getNickName(),userDetail.getUserId(),request.getURI().getPath());
            return response(exchange, CommonErrorCode.AUTH_NOT_ACCESS_GATEWAY);
        }*/
        //向headers中放文件，记得build
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        //将现在的request 变成 exchange对象
        ServerWebExchange webExchange = exchange.mutate().request(serverHttpRequest).build();

        return chain.filter(webExchange);
    }


    @Override
    public int getOrder() {
        return AUTH_FILTER_ORDER;
    }

    /**
     * 校验接口权限
     * @param bizType
     * @param userDetail
     * @param apiDefine
     * @return
     */
    /*private boolean checkAuth(BizType bizType,UserDetail userDetail,ApiDefineVO apiDefine){

        return false;
    }*/
}
