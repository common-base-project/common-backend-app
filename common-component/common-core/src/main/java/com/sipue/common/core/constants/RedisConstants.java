package com.sipue.common.core.constants;

public interface RedisConstants {

    /**
     * redis的key项目前缀
     */
    String PUBLIC_PREFIX = "sipue:";
    /**
     * 权限redis key前缀
     */
    String REDIS_AUTH_KEY=PUBLIC_PREFIX+"auth:";
    /**
     * 登录令牌Redis key
     */
    String ACCESS_TOKEN_KEY = REDIS_AUTH_KEY+"token:biz-%s:platform-%s:accessToken:%s";
    /**
     * 刷新令牌Redis key
     */
    String REFRESH_TOKEN_KEY = REDIS_AUTH_KEY+"token:biz-%s:platform-%s:refreshToken:%s";

    /**
     * 用户信息Redis key
     */
    String TOKEN_USER_KEY = REDIS_AUTH_KEY+"user:biz-%s:user-%d:platform-%s";

    /**
     * 重复登录 Redis Key
     */
    String REPEAT_TOKEN_KEY = REDIS_AUTH_KEY+"repeat:%s";
    /**
     * 强制下线
     */
    String FORCE_LOGOUT_KEY = REDIS_AUTH_KEY+"forceLogout:%s";

    /**
     * 用户中心redis key前缀
     */
    String USER_CENTER_KEY=PUBLIC_PREFIX + "user-center:";
    /**
     * 网关服务
     */
    String GATEWAY_CENTER_KEY = PUBLIC_PREFIX + "gateway-center:";
    /**
     * 消息服务
     */
    String MESSAGE_CENTER_KEY = PUBLIC_PREFIX + "message-center:";
    /**
     * 后台系统前缀
     */
    String BACKSTAGE_PREFIX = PUBLIC_PREFIX + "backstage:";
    /**
     * 转换缓存Key
     * @param prefix 前缀
     * @param key 键
     * @return 最终键值
     */
    static String serializeKey(String prefix, String key){
        if (prefix.endsWith(":")){
            return prefix + key;
        }else {
            return prefix + ":" + key;
        }
    }
}
