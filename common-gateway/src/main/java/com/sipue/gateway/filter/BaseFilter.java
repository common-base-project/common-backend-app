package com.sipue.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.sipue.common.core.exception.ServiceException;
import com.sipue.common.core.model.IErrorCode;
import com.sipue.common.core.model.Result;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Description 基础拦截器
 * @Author wangjunyu
 * @Date 2022年6月06日17:25:25
 * @Version 1.0
 */
public abstract class BaseFilter implements GlobalFilter, Ordered {

    /**
     * 1、跨越拦截
     */
    static Integer CORS_FILTER_ORDER = -500;

    /**
     * 2、获取请求参数
     */
    static Integer POST_BODY_FILTER_ORDER = -140;

    /**
     * 3、TRACE ID 链路地址
     */
    static Integer TRACE_ID_FILTER_ORDER = -130;

    /**
     * 4、校验请求参数
     */
    static Integer HTTP_SIGN_FILTER_ORDER = -120;

    /**
     * 5、权限校验
     * <p>
     * 校验是否登录
     * 根据角色校验接口请求权限
     */
    static Integer AUTH_FILTER_ORDER = -110;
    /**
     * 请求体
     */
    static String POST_REQUEST_BODY="POST_REQUEST_BODY";

    /**
     * 响应错误码
     *
     * @param exchange
     * @param errorCode
     * @return
     */
    public Mono<Void> response(ServerWebExchange exchange, IErrorCode errorCode) {
        ServerHttpResponse response = exchange.getResponse();
        Result result = Result.fail(errorCode);
        //byte[] bits = result.toString().getBytes(StandardCharsets.UTF_8);
        byte[] bits = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.OK);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 响应错误码
     *
     * @param exchange
     * @param ex
     * @return
     */
    public Mono<Void> response(ServerWebExchange exchange, ServiceException ex) {
        ServerHttpResponse response = exchange.getResponse();
        Result result = Result.fail(ex);
        //byte[] bits = result.toString().getBytes(StandardCharsets.UTF_8);
        byte[] bits = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.OK);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

}
