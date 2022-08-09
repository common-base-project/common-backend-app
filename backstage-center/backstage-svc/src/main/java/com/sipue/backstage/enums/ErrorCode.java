package com.sipue.backstage.enums;

import com.sipue.common.core.model.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 后台管理服务错误码 110000-119999
 *
 * @Author: wangjunyu
 * @Date: 2022/7/11 17:21
 */
@Getter
@AllArgsConstructor
public enum ErrorCode implements IErrorCode {


    //用户 110001 -- 110100
    USER_NOT_FOUND("110001","用户不存在"),
    USER_ALREADY_EXISTS("110002","用户信息已存在"),
    USER_NOT_MATCH("110003","账号或密码错误"),

    //角色 110101 -- 110201
    ;

    private String code;

    private String msg;

}