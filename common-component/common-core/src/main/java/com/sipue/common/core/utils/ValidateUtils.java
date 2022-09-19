package com.sipue.common.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:验证相关工具类
 * @Author wangjunyu
 */
public class ValidateUtils {

    /**
     * 验证邮箱
     * 正确返回true
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    /*
      检查多个邮箱是否正确
     */
    public static boolean checkEmail(String [] email) {
        boolean flag = false;
        try {
            for (String s : email) {
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(s);
                flag = matcher.matches();
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        boolean aaa=checkEmail("aada@161.com");
        System.out.println(aaa);
    }
}
