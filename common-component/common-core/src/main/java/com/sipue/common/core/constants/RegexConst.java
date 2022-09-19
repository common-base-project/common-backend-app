package com.sipue.common.core.constants;

/**
 * @Description 常用正则表达式
 * @Author mustang
 * @Date 2022年6月28日18:57:07
 * @Version 1.0
 */
public interface RegexConst {

    String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    //1,6,8开头 0到9开头(第二位)  后面9个数字
    String PHONE_REGEX = "^([168][0-9])\\d{9}$";
    //帐号是否合法(字母开头，允许1-16字节，允许字母数字下划线)：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
    String ACCOUNT = "[a-zA-Z0-9_]{1,16}$";
    //密码限制6-15个字符，至少包含一个数字和一个字母
    String PASSWORD ="[a-zA-Z0-9_]{6,16}$";
}
