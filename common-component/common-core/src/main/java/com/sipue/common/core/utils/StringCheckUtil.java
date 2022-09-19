package com.sipue.common.core.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: mustang
 */
public class StringCheckUtil {

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * @param  str 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int getLength(String str) {
        /*
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            // 获取一个字符
            String temp = str.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            } else {
                // 其他字符长度为0.5
                valueLength += 1;
            }
        }
*/
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < str.length(); i++) {
            /* 获取一个字符 */
            String temp = str.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 检查字符长度是否超出限制
     * @param str
     * @params maxLen
     * @return
     */
    public static boolean checkStrLen(String str,int maxLen){
        if(StringUtils.isEmpty(str))return true;
        if(getLength(str)>maxLen){
            return false;
        }
        return true;
    }


    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            //System.out.println("不是数字");
            return false;
        }else {
            //System.out.println("是数字");
            return true;
        }
    }

    public static void main(String[] args) {
        String str="11试试。#";
        int len=getLength(str);
        System.out.println(len);

        boolean a=isNumber("-00000");
        System.out.println(a);

    }
}
