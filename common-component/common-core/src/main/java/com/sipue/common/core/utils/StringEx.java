package com.sipue.common.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * 字符串扩展
 *
 * @Author mustang
 * @version 1.0
 * @date 2022年6月30日19:37:33
 */
public class StringEx {
    /**
     * 下划线字符
     */
    private static final char UNDERLINE = '_';

    /**
     * 将驼峰命名规则转换为下划线写法，用于将java的字段名映射为数据库的字段名。
     * 首字母始终以小写开头。示例：
     * userName -> user_name    password ->password
     * UserEntity -> user_entity
     **/
    public static String toUnderlineName(String str) {
        //null或空字符串""
        int len = (str == null) ? 0 : str.length();
        if (len == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                //首字母大写时不添加下划线且转换为小写
                if (i > 0) {
                    sb.append(UNDERLINE);
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取异常详细信息
     * @param ex
     * @return
     */
    public static String getExceptionAllInfo(Exception ex) {
        ByteArrayOutputStream out = null;
        PrintStream pout = null;
        String ret = "";
        try {
            out = new ByteArrayOutputStream();
            pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray());
            out.close();
        } catch (Exception e) {
            return ex.getMessage();
        } finally {
            if (pout != null) {
                pout.close();
            }
        }
        return ret;
    }
}
