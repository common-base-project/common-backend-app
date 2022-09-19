package com.sipue.common.auth.store.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.sipue.common.auth.model.AuthAccessToken;
import com.sipue.common.auth.model.AuthRefreshToken;
import com.sipue.common.auth.model.Authority;
import com.sipue.common.auth.properties.AuthProperties;
import com.sipue.common.auth.store.TokenStore;
import com.sipue.common.core.constants.RedisConstants;
import com.sipue.common.core.enums.BizType;
import com.sipue.common.core.enums.Platform;
import com.sipue.common.core.model.Session;
import com.sipue.common.core.model.UserDetail;
import com.sipue.common.redis.template.RedisRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description 基于Redis的令牌存储方式
 * @Author wangjunyu
 * @Date 2022年6月28日10:39:59
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
public class RedisTokenStore implements TokenStore {

    /**
     * 认证服务端使用
     * 生成和 解析token
     */
    private final AuthProperties authProperties;

    private final RedisRepository redisRepository;
    /**
     * 创建认证token
     *
     * @param userDetail 用户信息
     * @param bizType     系统标识
     * @param platform    平台标识
     * @return token
     */
    @Override
    public AuthAccessToken createAccessToken(UserDetail userDetail, BizType bizType, Platform platform){
        return createAccessToken(userDetail,bizType,platform,null);
    }

    /**
     * 创建认证token
     *
     * @param userDetail   用户信息
     * @param bizType     系统标识
     * @param platform    平台标识
     * @param expireMillis 过期时间(秒)
     * @return token
     */
    @Override
    public AuthAccessToken createAccessToken(UserDetail userDetail, BizType bizType, Platform platform, Long expireMillis) {
        String accessToken = UUID.randomUUID().toString().replaceAll("-","");
        if (expireMillis == null || expireMillis <= 0) {
            expireMillis = authProperties.getExpire();
        }
        long nowMillis = System.currentTimeMillis();
        //添加Token过期时间
        //allowedClockSkewMillis
        long expMillis = nowMillis + expireMillis * 1000;
        LocalDateTime expireTime= LocalDateTimeUtil.of(expMillis);

        AuthAccessToken tokenInfo = new AuthAccessToken();
        tokenInfo.setUserId(userDetail.getUserId());
        tokenInfo.setNickName(userDetail.getNickName());
        tokenInfo.setPhone(userDetail.getPhone());
        tokenInfo.setPlatform(platform.name());
        tokenInfo.setBizType(bizType.name());
        //tokenInfo.setTokenType(BEARER_HEADER_KEY);
        tokenInfo.setToken(accessToken);
        tokenInfo.setExpire(expMillis);
        tokenInfo.setExpireTime(expireTime);
        AuthRefreshToken refreshToken = createRefreshToken(userDetail,bizType,platform);

        tokenInfo.setRefreshToken(refreshToken.getRefreshToken());
        //保存登录令牌到redis
        storeAuthAccessToken(tokenInfo);

        //保存用户信息到redis
        Authority authority = new Authority();
        BeanUtils.copyProperties(tokenInfo, authority);
        authority.setUserDetail(userDetail);
        storeAuthority(authority,refreshToken.getExpire());

        return tokenInfo;
    }

    /**
     * 创建refreshToken
     *
     * @param userDetail 用户信息
     * @param bizType     系统标识
     * @param platform    平台标识
     * @return refreshToken
     */
    private AuthRefreshToken createRefreshToken(UserDetail userDetail, BizType bizType, Platform platform) {
        String refreshToken = UUID.randomUUID().toString().replaceAll("-","");
        long nowMillis = System.currentTimeMillis();
        //Date now = new Date(nowMillis);
        //添加Token过期时间
        long expMillis = nowMillis + authProperties.getRefreshExpire() * 1000;
        LocalDateTime expireTime= LocalDateTimeUtil.of(expMillis);

        AuthRefreshToken refreshTokenInfo=new AuthRefreshToken();
        refreshTokenInfo.setUserId(userDetail.getUserId());
        refreshTokenInfo.setRefreshToken(refreshToken);
        refreshTokenInfo.setExpire(authProperties.getRefreshExpire());
        refreshTokenInfo.setExpireTime(expireTime);
        //保存刷新令牌到redis
        storeAuthRefreshToken(refreshTokenInfo,bizType.name(),platform.name());
        return refreshTokenInfo;
    }

    /**
     * 保存登录令牌
     *
     * @param authAccessToken token
     */
    private void storeAuthAccessToken(AuthAccessToken authAccessToken) {
        String redisKey = String.format(RedisConstants.ACCESS_TOKEN_KEY,
                authAccessToken.getBizType(),
                authAccessToken.getPlatform(),
                authAccessToken.getToken()).toLowerCase();
        redisRepository.putObject(redisKey, authAccessToken, authAccessToken.getExpire());
        log.info("保存登录令牌:"+authAccessToken.getUserId());
    }
    /**
     * 保存刷新令牌
     *
     * @param authRefreshToken refreshToken
     * @param bizType     系统标识
     * @param platform    平台标识
     */
    private void storeAuthRefreshToken(AuthRefreshToken authRefreshToken,String bizType,String platform) {
        String redisKey = String.format(RedisConstants.REFRESH_TOKEN_KEY,
                bizType,
                platform,
                authRefreshToken.getRefreshToken()).toLowerCase();
        redisRepository.putObject(redisKey, authRefreshToken, authRefreshToken.getExpire());
    }

    /**
     * 保存用户信息
     *
     * @param authority 用户信息
     */
    private void storeAuthority(Authority authority,long ttl) {
        String redisKey = String.format(RedisConstants.TOKEN_USER_KEY,
                authority.getBizType(),
                authority.getUserDetail().getUserId(),
                authority.getPlatform()).toLowerCase();
        redisRepository.putObject(redisKey, authority, ttl);

        //String redisKey1 = String.format(RedisConstant.AUTH_REFRESH_TOKEN,authority.getRefreshToken());
        //redisRepository.putObject(serializeKey(redisKey1), authority, ttl);
    }

    /**
     * 获取token信息
     * @param bizType     系统标识
     * @param platform    平台标识
     * @param accessToken token
     * @return token
     */
    @Override
    public AuthAccessToken readAccessToken(BizType bizType,Platform platform,String accessToken) {
        if(StrUtil.isEmpty(accessToken))return null;
        String redisKey = String.format(RedisConstants.ACCESS_TOKEN_KEY,
                bizType.name(),
                platform.name(),
                accessToken).toLowerCase();
        return redisRepository.getObject(redisKey, AuthAccessToken.class);
    }

    /**
     * 获取refreshToken信息
     * @param bizType     系统标识
     * @param platform    平台标识
     * @param refreshToken token
     * @return token
     */
    @Override
    public AuthRefreshToken readRefreshToken(BizType bizType,Platform platform,String refreshToken) {
        if(StrUtil.isEmpty(refreshToken))return null;
        String redisKey = String.format(RedisConstants.REFRESH_TOKEN_KEY,
                bizType.name(),
                platform.name(),
                refreshToken).toLowerCase();
        return redisRepository.getObject(redisKey, AuthRefreshToken.class);
    }

    /**
     * 读取用户权限信息
     *
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param userId   用户Id
     * @return Authority 用户信息
     */
    @Override
    public Authority readAuthority(BizType bizType, Platform platform, Long userId) {
        String redisKey = String.format(RedisConstants.TOKEN_USER_KEY,
                bizType.name(),
                userId,
                platform.name()).toLowerCase();
        log.debug(redisKey);
        return redisRepository.getObject(redisKey, Authority.class);
    }

    /**
     * 设置重复登录
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param accessToken token
     */
    @Override
    public void repeatLoin(BizType bizType,Platform platform,String accessToken) {
        String redisKey = String.format(RedisConstants.ACCESS_TOKEN_KEY,
                bizType.name(),
                platform.name(),
                accessToken).toLowerCase();
        if (redisRepository.hasKey(redisKey)){
            long ttl = redisRepository.getExpire(redisKey);
            String repeatRedisKey = String.format(RedisConstants.REPEAT_TOKEN_KEY, accessToken).toLowerCase();
            redisRepository.putString(repeatRedisKey,accessToken,ttl);
        }
    }

    /**
     * 检查是否重复登录
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param accessToken token
     * @return
     */
    @Override
    public boolean checkRepeatLogin(BizType bizType,Platform platform,String accessToken) {
        String repeatRedisKey = String.format(RedisConstants.REPEAT_TOKEN_KEY, accessToken).toLowerCase();
        String val = redisRepository.getString(repeatRedisKey);
        return Objects.nonNull(val);
    }

    /**
     * 强制下线
     *
     * @param bizType 系统标识
     * @param userId 用户id
     */
    @Override
    public void forceLogout(BizType bizType, Long userId) {
        for (Platform platform : Platform.values()){
            Authority authority = readAuthority(bizType,platform,userId);
            if (Objects.nonNull(authority)){
                //记录强制下线
                String redisKey = String.format(RedisConstants.ACCESS_TOKEN_KEY,bizType.name(), platform.name(),authority.getToken()).toLowerCase();
                if (redisRepository.hasKey(redisKey)){
                    long ttl = redisRepository.getExpire(redisKey);
                    String forceLogoutKey = String.format(RedisConstants.FORCE_LOGOUT_KEY, authority.getToken()).toLowerCase();
                    redisRepository.putString(forceLogoutKey,authority.getToken(),ttl);
                }
                //删除已有令牌信息
                removeAuthAccessToken(bizType,platform,authority.getToken());
                removeAuthRefreshToken(bizType,platform,authority.getRefreshToken());
                removeAuthority(bizType,authority.getUserDetail().getUserId());
            }
        }
    }

    /**
     * 检测是否为强制下线
     * @param accessToken 登录令牌
     */
    @Override
    public boolean checkForceLogout(String accessToken) {
        String repeatRedisKey = String.format(RedisConstants.FORCE_LOGOUT_KEY, accessToken);
        String val = redisRepository.getString(repeatRedisKey);
        return Objects.nonNull(val);
    }

    /**
     * 移除登录令牌
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param accessToken 登录令牌
     */
    @Override
    public void removeAuthAccessToken(BizType bizType,Platform platform,String accessToken) {
        String redisKey = String.format(RedisConstants.ACCESS_TOKEN_KEY, bizType.name(), platform.name(),accessToken).toLowerCase();
        redisRepository.delete(redisKey);
    }

    /**
     * 移除刷新令牌（同时移除权限信息）
     * @param bizType  系统标识
     * @param platform 登录平台标识
     * @param refreshToken 刷新令牌
     */
    @Override
    public void removeAuthRefreshToken(BizType bizType,Platform platform,String refreshToken) {
        String redisKey = String.format(RedisConstants.REFRESH_TOKEN_KEY, bizType.name(), platform.name(),refreshToken).toLowerCase();
        redisRepository.delete(redisKey);
    }
    /**
     * 删除权限对象
     * @param bizType  系统标识
     * @param userId   用户id
     */
    @Override
    public void removeAuthority(BizType bizType,Long userId){
        for (Platform platform : Platform.values()) {
            Authority authority = readAuthority(bizType, platform, userId);
            if (Objects.nonNull(authority)) {
                String redisKey = String.format(RedisConstants.TOKEN_USER_KEY,bizType.name(),userId,platform.name()).toLowerCase();
                redisRepository.delete(redisKey);
            }
        }
    }

    /**
     * 重置认证token
     *
     * @param authority 用户信息
     * @return token
     */
    @Override
    public AuthAccessToken resetAccessToken(Authority authority) {

        AuthAccessToken authAccessToken = readAccessToken(BizType.getBizTypeByCode(authority.getBizType()), Session.getPlatform(), authority.getToken());
        AuthAccessToken tokenInfo = null;
        long expireMillis=authProperties.getExpire();
        //判断token是否存在,如果存在则重置失效时间,如果不存在则生成token
        if(Objects.nonNull(authAccessToken)) {
            expireMillis=authAccessToken.getExpire();
            tokenInfo=authAccessToken;
        }else{
            String accessToken = UUID.randomUUID().toString().replaceAll("-","");
            UserDetail userDetail=authority.getUserDetail();
            tokenInfo=new AuthAccessToken();
            tokenInfo.setUserId(userDetail.getUserId());
            tokenInfo.setNickName(userDetail.getNickName());
            tokenInfo.setPhone(userDetail.getPhone());
            tokenInfo.setPlatform(authority.getPlatform());
            tokenInfo.setBizType(authority.getBizType());
            //tokenInfo.setTokenType(BEARER_HEADER_KEY);
            tokenInfo.setToken(accessToken);
        }
        long nowMillis = System.currentTimeMillis();
        //添加Token过期时间
        //allowedClockSkewMillis
        long expMillis = nowMillis + expireMillis * 1000;
        LocalDateTime expireTime= LocalDateTimeUtil.of(expMillis);

        tokenInfo.setExpire(expireMillis);
        tokenInfo.setExpireTime(expireTime);
        AuthRefreshToken refreshToken =resetRefreshToken(tokenInfo);

        tokenInfo.setRefreshToken(refreshToken.getRefreshToken());
        //保存登录令牌到redis
        storeAuthAccessToken(tokenInfo);

        //保存用户信息到redis
        BeanUtils.copyProperties(tokenInfo, authority);

        storeAuthority(authority,refreshToken.getExpire());

        return tokenInfo;
    }

    /**
     * 刷新用户缓存信息
     * @param userDetail
     */
    @Override
    public void refreshUserCache(UserDetail userDetail) {
        for (Platform platform : Platform.values()) {
            for (BizType bizType : BizType.values()) {
                String redisKey1 = String.format(RedisConstants.TOKEN_USER_KEY,bizType.name(),userDetail.getUserId(),platform.name()).toLowerCase();
                Authority authority = readAuthority(bizType, platform, userDetail.getUserId());
                if (Objects.nonNull(authority)) {
                    long ttl1 = redisRepository.getExpire(redisKey1);
                    UserDetail oldUserDetail = authority.getUserDetail();
                    oldUserDetail.setPhone(StrUtil.isNotBlank(userDetail.getPhone())?userDetail.getPhone(): oldUserDetail.getPhone());
                    oldUserDetail.setNickName(StrUtil.isNotBlank(userDetail.getNickName())?userDetail.getNickName(): oldUserDetail.getNickName());
                    redisRepository.putObject(redisKey1, authority, ttl1);

                    //更新token内用户信息
                    String redisKey2 = String.format(RedisConstants.ACCESS_TOKEN_KEY, bizType.name(), platform.name(), authority.getToken()).toLowerCase();
                    AuthAccessToken authAccessToken=readAccessToken(bizType, platform, authority.getToken());
                    if (Objects.nonNull(authAccessToken)) {
                        long ttl2 = redisRepository.getExpire(redisKey2);
                        authAccessToken.setPhone(StrUtil.isNotBlank(userDetail.getPhone())?userDetail.getPhone(): oldUserDetail.getPhone());
                        authAccessToken.setNickName(StrUtil.isNotBlank(userDetail.getNickName())?userDetail.getNickName(): oldUserDetail.getNickName());
                        redisRepository.putObject(redisKey2, authAccessToken, ttl2);
                    }
                }
            }
        }
    }

    /**
     * 重置refreshToken
     *
     * @param authAccessToken token信息
     * @return refreshToken
     */
    private AuthRefreshToken resetRefreshToken(AuthAccessToken authAccessToken) {
        long nowMillis = System.currentTimeMillis();
        //Date now = new Date(nowMillis);
        //添加Token过期时间
        long expMillis = nowMillis + authProperties.getRefreshExpire() * 1000;
        LocalDateTime expireTime= LocalDateTimeUtil.of(expMillis);
        String redisKey = String.format(RedisConstants.REFRESH_TOKEN_KEY,
                authAccessToken.getBizType(),
                authAccessToken.getPlatform(),
                authAccessToken.getRefreshToken()).toLowerCase();
        AuthRefreshToken refreshTokenInfo = redisRepository.getObject(redisKey, AuthRefreshToken.class);
        if(Objects.isNull(refreshTokenInfo)) {
            refreshTokenInfo = new AuthRefreshToken();
            refreshTokenInfo.setUserId(authAccessToken.getUserId());
            refreshTokenInfo.setRefreshToken(authAccessToken.getRefreshToken());
            refreshTokenInfo.setExpire(authProperties.getRefreshExpire());
            refreshTokenInfo.setExpireTime(expireTime);
        }else{
            refreshTokenInfo.setExpire(authProperties.getRefreshExpire());
            refreshTokenInfo.setExpireTime(expireTime);
        }
        //保存刷新令牌到redis
        storeAuthRefreshToken(refreshTokenInfo, authAccessToken.getBizType(), authAccessToken.getPlatform());
        return refreshTokenInfo;
    }
}
