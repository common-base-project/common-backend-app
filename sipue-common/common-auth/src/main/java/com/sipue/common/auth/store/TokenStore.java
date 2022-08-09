package com.sipue.common.auth.store;

import com.sipue.common.auth.model.AuthAccessToken;
import com.sipue.common.auth.model.AuthRefreshToken;
import com.sipue.common.auth.model.Authority;
import com.sipue.common.core.model.UserDetail;
import com.sipue.common.core.enums.BizType;
import com.sipue.common.core.enums.Platform;

public interface TokenStore {

    /**
     * 创建认证token
     *
     * @param userDetail 用户信息
     * @param bizType    系统标识
     * @param platform   平台标识
     * @return token     令牌信息
     */
    AuthAccessToken createAccessToken(UserDetail userDetail, BizType bizType, Platform platform);

    /**
     * 创建信息的令牌信息
     *
     * @param userDetail  用户信息
     * @param bizType     系统标识
     * @param platform    平台标识
     * @param expireMillis 过期时间(秒)
     * @return            令牌信息
     */
    AuthAccessToken createAccessToken(UserDetail userDetail, BizType bizType, Platform platform, Long expireMillis);

    /**
     * 读取登录令牌信息
     *
     * @param accessToken
     * @return
     */
    AuthAccessToken readAccessToken(BizType bizType, Platform platform,String accessToken);

    /**
     * 读取刷新令牌信息
     *
     * @param refreshToken
     * @return
     */
    AuthRefreshToken readRefreshToken(BizType bizType, Platform platform,String refreshToken);

    /**
     * 读取用户权限信息
     *
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param userId   用户Id
     * @return
     */
    Authority readAuthority(BizType bizType, Platform platform, Long userId);
    /**
     * 设置重复登录
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param accessToken token
     */
    void repeatLoin(BizType bizType,Platform platform,String accessToken);

    /**
     * 检查是否重复登录
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param accessToken token
     * @return
     */
    boolean checkRepeatLogin(BizType bizType,Platform platform,String accessToken);

    /**
     * 强制下线
     * @param bizType
     * @param userId
     */
    void forceLogout(BizType bizType,Long userId);

    /**
     * 检测是否为强制下线
     * @param token
     */
    boolean checkForceLogout(String token);

    /**
     * 移除登录令牌
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param accessToken token
     */
    void removeAuthAccessToken(BizType bizType, Platform platform,String accessToken);

    /**
     * 移除刷新令牌（同时移除权限信息）
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param refreshToken refreshToken
     */
    void removeAuthRefreshToken(BizType bizType, Platform platform,String refreshToken);
    /**
     * 删除权限对象
     * @param bizType  系统标识
     * @param userId   用户id
     */
    void removeAuthority(BizType bizType,Long userId);
    /**
     * 重置认证token
     * @param authority 用户信息
     * @return token
     */
    AuthAccessToken resetAccessToken(Authority authority);

    /**
     * 刷新用户缓存信息
     * @param userDetail
     */
    void refreshUserCache(UserDetail userDetail);
}
