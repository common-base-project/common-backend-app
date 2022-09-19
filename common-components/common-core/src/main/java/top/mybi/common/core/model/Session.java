package top.mybi.common.core.model;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import top.mybi.common.core.constants.GlobalConstants;
import top.mybi.common.core.constants.HeaderConstants;
import top.mybi.common.core.enums.BizType;
import top.mybi.common.core.enums.Platform;
import top.mybi.common.core.exception.ServiceException;
import top.mybi.common.core.utils.HttpUtil;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 当前请求信息
 * @Author mustang
 * @date 2022/1/25 9:46
 */
public class Session {

    /**
     * @Description: 获取请求ip
     *
     * @Author: mustang
     */
    public static String getRequestIp() {
        String requestIp = HttpUtil.getHeader(HeaderConstants.REQUEST_IP);
        return requestIp;
    }

    /**
     * 获取客户端信息
     *
     * @return
     */
    public static ClientInfo getClientInfo() {
        String appId = HttpUtil.getHeader(HeaderConstants.APP_ID);
        return new ClientInfo(appId);

    }
    /**
     * 获取客户端信息
     *
     * @return
     */
    public static ClientInfo getClientInfo(ServerHttpRequest request) {
        String appId = request.getHeaders().getFirst(HeaderConstants.APP_ID);
        return new ClientInfo(appId);

    }
    /**
     * 获取用户的业务类型
     *
     * @return
     */
    public static BizType getBizType() {
        return getClientInfo().getBizType();
    }

    /**
     * 获取用户的业务类型
     *
     * @return
     */
    public static BizType getBizType(ServerHttpRequest request) {
        return getClientInfo(request).getBizType();
    }
    /**
     * 获取客户端信息
     *
     * @return
     */
    public static Platform getPlatform() {
        return getClientInfo().getPlatform();
    }
    /**
     * 获取客户端信息
     *
     * @return
     */
    public static Platform getPlatform(ServerHttpRequest request) {
        return getClientInfo(request).getPlatform();
    }

    /**
     * 获取当前用户信息
     *
     * @return 当前用户
     */
    public static UserDetail getUser() {
        String header = HttpUtil.getHeader(HeaderConstants.CURRENT_USER_INFO);
        if (StrUtil.isEmpty(header)) {
            throw new ServiceException(CommonErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        UserDetail userDetail = JSONObject.parseObject(header, UserDetail.class);
        if(!StrUtil.isEmpty(userDetail.getNickName())) {
            try {
                userDetail.setNickName(URLDecoder.decode(userDetail.getNickName(), GlobalConstants.CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return userDetail;
    }

    /**
     * 获取当前用户Id
     *
     * @return 用户Id
     */
    public static Long getUserId() {
        return getUser().getUserId();
    }
    /**
     * 获取当前用户用户昵称
     *
     * @return 用户昵称
     */
    public static String getUserNickName() {
        return getUser().getNickName();
    }



    /**
     * @Description: 判断是否存在当前用户header
     *
     * @Author: mustang
     * @Date: 2022/6/9 14:23
     */
    public static boolean currentUserHeader() {
        String header = HttpUtil.getHeader(HeaderConstants.CURRENT_USER_INFO);
        return StringUtils.hasText(header)?true:false;
    }
}
