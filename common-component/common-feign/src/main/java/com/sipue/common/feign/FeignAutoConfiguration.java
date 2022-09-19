package com.sipue.common.feign;

import com.sipue.common.feign.config.DefaultErrorDecoder;
import com.sipue.common.feign.config.FeignInterceptorConfig;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * @Author mustang
 * @date 2022/1/20 9:48
 */
public class FeignAutoConfiguration {
    @Bean
    public ErrorDecoder errorDecoder(){
        return new DefaultErrorDecoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Bean
    public FeignInterceptorConfig feignInterceptorConfig(){
        return new FeignInterceptorConfig();
    }
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignInterceptorConfig().httpFeignInterceptor();
    }

}
