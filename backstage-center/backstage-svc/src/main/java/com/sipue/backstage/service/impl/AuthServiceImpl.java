package com.sipue.backstage.service.impl;

import com.sipue.backstage.entity.UserEntity;
import com.sipue.backstage.enums.ErrorCode;
import com.sipue.backstage.pojo.dto.auth.UserLoginDTO;
import com.sipue.backstage.pojo.vo.auth.LoginUserVO;
import com.sipue.backstage.pojo.vo.auth.TokenVO;
import com.sipue.backstage.pojo.vo.auth.UserLoginVO;
import com.sipue.common.core.model.role.RoleVO;
import com.sipue.backstage.service.IAuthService;
import com.sipue.backstage.service.IRoleService;
import com.sipue.backstage.service.IUserService;
import com.sipue.common.auth.model.AuthAccessToken;
import com.sipue.common.auth.model.Authority;
import com.sipue.common.auth.store.TokenStore;
import com.sipue.common.core.exception.ServiceException;
import com.sipue.common.core.model.Session;
import com.sipue.common.core.model.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 登录
 *
 * @author wangjunyu
 * @date 2022-07-11 17:05:16
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Resource
    private IUserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private IRoleService roleService;

    private static String USER_ID_CACHE_KEY = "user:userId-%d";
    private static String USER_PHONE_CACHE_KEY = "user:phone-%s";


    @Override
    public UserLoginVO login(UserLoginDTO params) {
        UserEntity userEntity = userService.getUserByPhone(params.getPhone());
        if (Objects.isNull(userEntity) || !passwordEncoder.matches(params.getPassword(), userEntity.getPassword())) {
            throw new ServiceException(ErrorCode.USER_NOT_MATCH);
        }
        //查询角色
        List<RoleVO> roles = roleService.getUserRoles(userEntity.getUserId());
        if(roles.size() <= 0){
            throw new ServiceException(ErrorCode.USER_ROLE_FOUND);
        }
        LoginUserVO loginUserVO = userEntity.covertBean(LoginUserVO.class);
        loginUserVO.setRoles(roles);
        List<String> perissions = roleService.getRolePermissions(roles.stream().map(RoleVO::getRoleId).collect(Collectors.toList()));
        loginUserVO.setPerissions(perissions);
        UserDetail userDetail = loginUserVO.covertBean(UserDetail.class);
        userDetail.setNickName(userEntity.getUserName());
        TokenVO tokenVO = createToken(userDetail);
        return UserLoginVO.builder().tokenInfo(tokenVO)
                .userInfo(loginUserVO)
                .build();
    }

    /**
     * 创建令牌信息
     *
     * @param userDetail 用户信息
     * @return
     */
    private TokenVO createToken(UserDetail userDetail) {
        Authority authority = tokenStore.readAuthority(Session.getBizType(), Session.getPlatform(), userDetail.getUserId());
        //已存在登录信息，删除并记录重复登录信息
        if (Objects.nonNull(authority)) {
            tokenStore.repeatLoin(Session.getBizType(),Session.getPlatform(),authority.getToken());
            tokenStore.removeAuthAccessToken(Session.getBizType(),Session.getPlatform(),authority.getToken());
            tokenStore.removeAuthRefreshToken(Session.getBizType(),Session.getPlatform(),authority.getRefreshToken());
            tokenStore.removeAuthority(Session.getBizType(),userDetail.getUserId());
        }
        AuthAccessToken authAccessToken = tokenStore.createAccessToken(userDetail, Session.getBizType(), Session.getPlatform());
        return authAccessToken.covertBean(TokenVO.class);
    }
}