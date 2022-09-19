package com.sipue.common.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.sipue.common.swagger.config.SwaggerProperties;
import io.swagger.models.auth.In;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

/**
 * @Author wangjunyu
 */

@EnableKnife4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    private SwaggerProperties swaggerProperties;

    public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                // 定义是否开启swagger，false为关闭，可以通过变量控制,swagger3.0此配置无需
                .enable(swaggerProperties.getEnabled())
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                // 接口调试地址
                .host(swaggerProperties.getTryHost())
                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/error").negate())
                .paths(PathSelectors.regex("/actuator.*").negate())
                .build()
                // 支持的通讯协议集合
                .protocols(newHashSet("https", "http"));
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getName() + " 接口文档")
                .description(swaggerProperties.getDescription())
                .contact(new Contact("wangjunyu", null, "wangjunyu@splsw.com"))
                .version("项目版本: " + swaggerProperties.getVersion() + ", Spring Boot Version: " + SpringBootVersion.getVersion())
                .build();
    }



    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }

    /**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     */
//     @SuppressWarnings("unchecked")
//     @Override
//     public void addInterceptors(InterceptorRegistry registry) {
//     try {
//     Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
//     List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
//     if (registrations != null) {
//     for (InterceptorRegistration interceptorRegistration : registrations) {
//     interceptorRegistration
//     .excludePathPatterns("/swagger**/**")
//            .excludePathPatterns("/webjars/**")
//                            .excludePathPatterns("/v3/**")
//                            .excludePathPatterns("/doc.html");
//}
//            }
//                    } catch (Exception e) {
//                    e.printStackTrace();
//                    }
//                    }

}
