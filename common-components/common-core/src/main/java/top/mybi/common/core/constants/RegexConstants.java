package top.mybi.common.core.constants;

/**
 * 常用正则表达式
 */
public interface RegexConstants {
    //用户名
    String USERNAME_REGEX = "^(?![^A-Za-z]+$)(?![^0-9]+$)[0-9A-Za-z_]{4,16}$";
    //邮箱
    String EMAIL_REGEX = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    //手机号 1,6,8开头 3到9开头(第二位)  后面9个数字
    String PHONE_REGEX = "^([1|6|8][3-9])\\d{9}$";
    //微信号
    //String WECHAT_REGEX="^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";
    String WECHAT_REGEX="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z-_]{28}$";

    //验证法院端帐号是否合法(字母开头，允许1-16字节，允许字母数字下划线)
    String ACCOUNT = "[a-zA-Z0-9_]{1,16}$";
    //密码限制6-15个字符，至少包含一个数字和一个字母
    String PASSWORD ="[a-zA-Z0-9_]{6,16}$";
}
