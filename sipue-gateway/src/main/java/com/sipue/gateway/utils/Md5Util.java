package com.sipue.gateway.utils;

import com.sipue.common.core.constants.HeaderConstants;
import com.sipue.common.core.enums.BizType;
import com.sipue.common.core.model.ClientInfo;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

/**
 * HttpSign加密
 * @Author wangjunyu
 * @date 2022/1/24 15:27
 */
public class Md5Util {
    /**
     * HttpSign加密规则
     * {body}_权限_时间戳_{userinfo}
     */
    public static String getHttpSignMd5(ServerHttpRequest request, Object requestBody, ClientInfo client, Long userId) {
        StringBuilder md5Data = new StringBuilder();
        md5Data.append(Objects.isNull(requestBody) ? "" : requestBody);
        md5Data.append("_");
        md5Data.append(client.getBizType().getCode());
        md5Data.append("_");
        md5Data.append(request.getHeaders().getFirst(HeaderConstants.TIMESTAMP));
        md5Data.append("_");
        md5Data.append(userId == null?"" : userId);
        return md5Data.toString();
    }
}
