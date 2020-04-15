package com.walking.project.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.walking.project.common.authorization.SecurityConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.walking.project.common.authorization.SecurityConsts.*;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/15 2:17 下午
 * @Description:
 */
@Component
public class JWTUtil {

    @Autowired
    private static JWTUtil jwtUtil;

    @PostConstruct
    public void init() {
        jwtUtil = this;
    }

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token) throws UnsupportedEncodingException {
        String secret = getClaim(token, SecurityConsts.ACCOUNT) + SECRET_KEY;
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param account
     * @param currentTimeMillis
     * @return
     */
    public static String sign(String account, String currentTimeMillis) throws UnsupportedEncodingException {
        // 帐号加JWT私钥加密
        String secret = account + SECRET_KEY;
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME*60*1000l);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim(SecurityConsts.ACCOUNT, account)
                .withClaim(SecurityConsts.CURRENT_TIME_MILLIS, currentTimeMillis)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
