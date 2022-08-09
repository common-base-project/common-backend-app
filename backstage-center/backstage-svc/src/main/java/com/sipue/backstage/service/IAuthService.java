package com.sipue.backstage.service;


import com.sipue.backstage.pojo.dto.auth.UserLoginDTO;
import com.sipue.backstage.pojo.vo.auth.UserLoginVO;

/**
 *
 *
 * @author wangjunyu
 * @date 2022-07-11 17:05:16
 */
public interface IAuthService {

    /**
     * @Description: 登录
     *
     * @Author: wangjunyu
     * @Date: 2022/7/11 18:25
     */
    UserLoginVO login(UserLoginDTO params);
}

