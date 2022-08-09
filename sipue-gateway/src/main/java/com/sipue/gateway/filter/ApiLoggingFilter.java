package com.sipue.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Objects;

/**
 * @author wangjunyu
 * <p>
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求的API调用过滤，记录接口的请求时间，方便日志审计、告警、分析等运维操作 2. 后期可以扩展对接其他日志系统
 * <p>
 */
@Slf4j
@Component
public class ApiLoggingFilter extends BaseFilter {

	private static final String START_TIME = "startTime";

	private static final String X_REAL_IP = "X-Real-IP";// nginx需要配置

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String method = request.getMethodValue();
		String host=request.getURI().getHost();
		String path=request.getURI().getPath();
		Object params=null;
		String contentType = request.getHeaders().getFirst("Content-Type");
		//此处要排除流文件类型,比如上传的文件
		if (HttpMethod.POST.toString().equalsIgnoreCase(method) && Objects.nonNull(contentType) && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
			params = exchange.getAttribute(POST_REQUEST_BODY);
		}else{
			params=request.getQueryParams();
		}
		String info = String.format("请求方式:{%s} 请求IP:{%s} 请求路径:{%s} 参数：%s", method, host,path, params);
		log.debug(info);
		exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			Long startTime = exchange.getAttribute(START_TIME);
			if (startTime != null) {
				Long executeTime = (System.currentTimeMillis() - startTime);
				List<String> ips = exchange.getRequest().getHeaders().get(X_REAL_IP);
				InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
				String clientIp = Objects.requireNonNull(remoteAddress).getAddress().getHostAddress();
				String ip = ips != null ? ips.get(0) : clientIp;
				ServerHttpResponse re=exchange.getResponse();
				String api = exchange.getRequest().getURI().getRawPath();
				int code = exchange.getResponse().getStatusCode() != null
						? exchange.getResponse().getStatusCode().value() : 500;
				// 当前仅记录日志，后续可以添加日志队列，来过滤请求慢的接口
				log.debug("请求结果：{}的请求接口：{}，响应状态码：{}，请求耗时：{}ms", ip, api, code, executeTime);

			}
		}));
	}

	@Override
	public int getOrder() {
		return TRACE_ID_FILTER_ORDER;
	}

}
