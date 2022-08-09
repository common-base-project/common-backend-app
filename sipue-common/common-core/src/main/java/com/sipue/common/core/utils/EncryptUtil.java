package com.sipue.common.core.utils;

import com.sipue.common.core.exception.ServiceException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author : wangjunyu
 * @version : V1.0
 * @packageName :  com.apexsoft.crm.auth.sso.utils
 * @description : 客户提供的加解密方式
 */
public class EncryptUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * 无向量AES加密，并对加密结果进行base64包装
     *
     * @param key
     * @param input
     * @return
     */
    public static String encryptByAes(String key, String input) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
            return new String(Base64.encodeBase64(crypted));
        } catch (Exception e) {
            logger.error("encrypt加密异常：", e);
            throw e;
        }
    }


    /**
     * 无向量AES解密，解密内容为base64封装后的内容
     *
     * @param key
     * @param input
     * @return
     */
    public static String decryptByAes(String key, String input) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));
            return new String(output);
        } catch (Exception e) {
            logger.error("decrypt解密异常：", e);
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            //需要加密的密码
            System.out.println(encryptByAes("WelcomeToPytWebs", "zqeasacourtadmin"));
            System.out.println(encryptByAes("WelcomeToPytWebs", "123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //需要解密的字符串
      //System.out.println(decryptByAes("8+6M0suPfE8TigUWRNml9g=="));
    }
    static  String key = "WelcomeToPytWebs";
    public static String decryptByAes(String val) {
        try {
            return decryptByAes(key,val);
        }catch (Exception e){
            throw new ServiceException("密码校验不正确！");
        }
    }
}
