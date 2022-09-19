package top.mybi.common.core.utils;

import cn.hutool.core.codec.Base64Encoder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Desc
 * @Author mustang
 */
public class Base64Util {
    public static String Img2Base64(InputStream in){
        byte[] data = null;
        try {
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        String encode = Base64Encoder.encode(data);
        return "data:image/png;base64,"+encode;// 返回Base64编码过的字节数组字符串
    }
}