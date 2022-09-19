package top.mybi.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author mustang
 */
@Getter
@AllArgsConstructor
public enum CommonErrorCode implements IErrorCode{
    //基础错误码
    SUCCESS("0","响应成功"),
    ERROR("-1","请求异常，请稍后再试！"),
    SERVER_ERROR("-1","抱歉，服务器开了下小差，请稍后再试！"),
    SERVER_LOCK_ERROR("102","服务器加锁失败"),
    SERVER_CUSTOM_ERROR("500","服务请求异常，请稍后再试！"),
    FEIGN_CLIENT_ERROR("503","内部服务请求异常！"),
    //请求相关
    HTTP_SIGN_FAILED("1001","请求参数签名验证失败!"),
    REQUEST_PARAM_ERROR("1002", "请求参数错误!"),
    REQUEST_INVALID_PARAM("1003", "请求参数验证失败!"),
    REQUEST_PARAM_NOT_JSON("1007", "无效的请求参数，请检查请求参数格式是否正确"),
    REQUEST_APPID_EMPTY_OR_INVALID("1009", "请求APPID为空或者无效的请求标识!"),

    //验证相关
    VALIDATE_CODE_ERROR("3001","验证错误!"),
    API_NOT_FOUND("4009","请求地址不存在"),

    //登录相关
    ACCESS_TOKEN_EXPIRED("4004","账号登录失效，请重新登录"),
    AUTH_NOT_ACCESS_GATEWAY("4001","无权访问，请联系技术支持"),
    AUTH_NOT_ACCESS("4002","无权访问，请联系管理员"),
    ACCESS_TOKEN_EMPTY("4003", "缺少认证令牌参数"),
    ACCOUNT_LOGIN_IN_OTHER_REGION("4006", "您的账号已在其他地方登录，请确认账号安全"),
    ACCOUNT_FORCE_LOGOUT("4007", "您的账号发生变更，已被强制下线。请重新登录！"),
    ACCOUNT_USER_NOT_NULL("4008", "账号已存在！"),


    ;
    private String code;

    private String msg;
}
