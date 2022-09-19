package top.mybi.user.enums;


import top.mybi.common.core.model.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 用户中心错误码100000-109999
 *
 * @Author: mustang
 * @Date: 2022/7/7 16:34
 */
@Getter
@AllArgsConstructor
public enum ErrorCode implements IErrorCode {

    //权限 100000 --100100
    LOGIN_FAILED_PASSWORD_NOT_MATCH("100001","登录失败，用户密码不正确！");


    private String code;

    private String msg;

}
