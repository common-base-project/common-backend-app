package com.sipue.common.feign.config;

import com.sipue.common.feign.utils.ThreadLocalUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description Feign请求拦截器 同时添加请求头
 * @Author wangjunyu
 * @Date 2022年6月05日13:37:31
 * @Version 1.0
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //juint时为空
        if (attributes != null){
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            Set<String> header = new HashSet<>();
            if (headerNames != null){
                while (headerNames.hasMoreElements()){
                    String name = headerNames.nextElement();
                    header.add(name);
                    String values = request.getHeader(name);
                    requestTemplate.header(name,values);
                }
            }
        }
        //异步的时候需要
        ServletRequestAttributes servletRequestAttributes = ThreadLocalUtil.attributesThreadLocal.get();
        if (servletRequestAttributes != null){
            ServletRequestAttributes requestAttributes = servletRequestAttributes;
            HttpServletRequest request = requestAttributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            Set<String> header = new HashSet<>();
            if (headerNames != null){
                while (headerNames.hasMoreElements()){
                    String name = headerNames.nextElement();
                    header.add(name);
                    String values = request.getHeader(name);
                    requestTemplate.header(name,values);
                }
            }
            //用完记得删除
            ThreadLocalUtil.attributesThreadLocal.remove();
        }
    }
}
