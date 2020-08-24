package com.walking.project.utils;


import java.util.Objects;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:03
 * @Description:MD5加密Util.
 */
public class MD5Encrypt {

    private final static Logger logger = LoggerFactory.getLogger(MD5Encrypt.class);

    private static final String salt = "!!!@#$WALKING*SALT$#@!!!";

    /**
     * 带秘钥加密
     * @param text 明文
     * @return 密文
     */
    public static String md5Encrypt(String text) {
        try {
            // 加密后的字符串
            String md5str = DigestUtils.md5Hex(text + salt);
            return md5str;
        } catch (Exception e) {
            logger.error("加密失败: {}" , e);
            return "";
        }
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static Boolean verify(String text, String md5) {
        try {
            //根据传入的密钥进行验证
            String md5Text = md5Encrypt(text);
            if(Objects.equals(md5Text, md5)){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("加密失败: {}" , e);
            return false;
        }
    }
}
