package com.walking.project.common.authorization;

import java.util.*;

import com.google.common.collect.Sets;
import com.walking.project.dataobject.entity.User;
import com.walking.project.mapper.UserMapper;
import com.walking.project.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import static com.walking.project.common.ProjectConstant.ROLE_MAP;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = JWTUtils.getAccount(principals.toString());
        User user = userMapper.selectByAccount(account);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(ROLE_MAP.get(user.getRoleId()));
        Set<String> permission = StringUtils.isNotBlank(user.getPermission()) ?
            new HashSet<>(Arrays.asList(user.getPermission().split(","))) : Sets.newHashSet();
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = JWTUtils.getAccount(token);
        if (account == null) {
            throw new AuthenticationException("token invalid");
        }
        User user = userMapper.selectByAccount(account);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (! JWTUtils.verify(token, account, user.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

}
