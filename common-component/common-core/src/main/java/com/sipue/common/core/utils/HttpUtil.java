package com.sipue.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.sipue.common.core.model.IErrorCode;
import com.sipue.common.core.model.Result;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description HTTP工具类
 * @Author mustang
 * @Date 2022年6月28日12:53:15
 * @Version 1.0
 */
public class HttpUtil {

    /**
     * 获取servlet request 请求对象
     */
    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }


    /**
     * 获取请求头内容
     *
     * @param headerName
     * @return
     */
    public static String getHeader(String headerName) {
        return getHttpRequest().getHeader(headerName);
    }

    /**
     * 响应数据
     *
     * @param response
     * @param errorCode
     * @throws IOException
     */
    public static void responseErrorCode(HttpServletResponse response, IErrorCode errorCode) throws IOException {
        // 重置response
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println(JSON.toJSONString(Result.fail(errorCode)));
    }

}
