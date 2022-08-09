package com.sipue.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangjunyu
 * @date 2022/2/8 19:34
 */
@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    /**
     * RouteLocator，GatewayProperties这两个类都是springcloud提供的springbean对象直接注入即可
     */
    private final RouteLocator routeLocator;
    //gateway配置文件
    private final GatewayProperties gatewayProperties;

    public static final String API_URI = "/v3/api-docs";

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        //从配置文件中获取并配置SwaggerResource
        gatewayProperties.getRoutes().stream()
            //过滤路由
            .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
            //循环添加，从路由的断言中获取，一般来说路由都会配置断言Path信息，这就不多说了
            .forEach(route -> {
                log.debug(route.getId());
                route.getPredicates().stream()
                        //获取Path信息
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        //开始添加SwaggerResource
                        .forEach(predicateDefinition -> resources.add(
                                swaggerResource(
                                        route.getId(),
                                        predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI))
                                )
                        );
            });

        return resources;
    }

    /**
     *
     * @param name 显示的名字
     * @param location 文档地址:/api/user/v3/api-docs
     * @return
     */
    private SwaggerResource swaggerResource(String name, String location) {
        String serviceNameCN=name;
        switch (name){
            case "user-center":serviceNameCN="用户中心";break;
            case "uaa-center":serviceNameCN="认证中心";break;
            case "backstage-center":serviceNameCN="后台中心";break;

            default:break;
        }
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(serviceNameCN);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(DocumentationType.OAS_30.getVersion());
        return swaggerResource;
    }

}
