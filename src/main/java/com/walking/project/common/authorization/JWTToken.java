package com.walking.project.common.authorization;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/15 2:02 下午
 * @Description:
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
