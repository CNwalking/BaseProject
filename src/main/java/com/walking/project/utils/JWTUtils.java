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
public class JWTUtils {

    /**
     * 校验正确性
     * @param token 密钥
     * @param account 账号
     * @param secret 其实是密码
     * @return
     */
    public static boolean verify(String token, String account, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(ACCOUNT, account)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 生成签名,一天后过期
     * @param account 账号
     * @param secret 密码
     * @return 加密的token
     */
    public static String sign(String account, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account信息
            return JWT.create()
                    .withClaim(ACCOUNT, account)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


    public static String getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(ACCOUNT).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
