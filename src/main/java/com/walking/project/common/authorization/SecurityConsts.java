package com.walking.project.common.authorization;

import lombok.Data;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/15 2:22 下午
 * @Description:
 */
@Data
public class SecurityConsts {
    // token过期时间，一天
    public static final Integer TOKEN_EXPIRE_TIME = 24*60*60*1000;
    // RefreshToken过期时间，2小时
    public static final Integer REFRESH_CHECK_TIME = 2*60*60*1000;
    // Shiro缓存有效期，单位分钟
    public static final Integer SHIRO_CACHE_EXPIRE_TIME = 120;
    // token加密密钥
    public static final String SECRET_KEY = "!@#$walking$#@!";

    public static final String REQUEST_AUTH_HEADER = "Authorization";

    public static final String ACCOUNT = "account";

    public static final String CURRENT_USER = "currentUser";

    public static final String CURRENT_TIME_MILLIS = "currentTime";


}
