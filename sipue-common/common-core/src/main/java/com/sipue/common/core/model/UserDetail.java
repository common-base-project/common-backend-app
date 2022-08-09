package com.sipue.common.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @Description 当前用户信息
 * 用户访问接口时，由网关根据token放入请求头中
 * @Author wangjunyu
 * @Date 2022年6月28日13:38:10
 * @Version 1.0
 */
@Setter
@Getter
public class UserDetail extends BaseModel {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     *
     */
    private String email;

    /**
     * 登录机构id
     */
    private Long orgId;

    /**
     * 登录团队id
     */
    private Long teamId;
}
