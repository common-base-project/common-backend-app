package com.sipue.common.auth.model;

import com.sipue.common.core.model.BaseModel;
import com.sipue.common.core.model.UserDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 用户权限信息
 * @Author mustang
 * @Date 2022年6月28日10:46:57
 * @Version 1.0
 */
@Getter
@Setter
public class Authority extends BaseModel {

    /**
     * 登录令牌
     */
    private String token;


    /**
     * 刷新令牌对象
     */
    private String refreshToken;

    /**
     * 平台
     */
    private String platform;

    /**
     * 业务系统标识
     */
    private String bizType;


    /**
     * 用户详细信息
     */
    private UserDetail userDetail;


}
