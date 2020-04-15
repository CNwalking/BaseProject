package com.walking.project.common.authorization;

import lombok.Data;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/15 2:22 下午
 * @Description:
 */
@Data
public class SecurityConsts {
    // token过期时间，2小时
    public static final Integer TOKEN_EXPIRE_TIME = 120;
    // RefreshToken过期时间，一天
    public static final Integer REFRESH_TOKEN_EXPIRE_TIME = 1440;
    // Shiro缓存有效期，单位分钟
    public static final Integer SHIRO_CACHE_EXPIRE_TIME = 120;
    // token加密密钥
    public static final String SECRET_KEY = "!@#$walking$#@!";

    public static final String REQUEST_AUTH_HEADER = "Authorization";

    public static final String ACCOUNT = "account";
    //Shiro redis 前缀
    public static final String PREFIX_SHIRO_CACHE = "cache:";
    //redis-key-前缀-shiro:refresh_token
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "refresh_token:";
    //JWT-currentTimeMillis
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";
}
