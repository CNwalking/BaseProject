package com.walking.project.service.impl;

import com.walking.project.common.Result;
import com.walking.project.common.ResultCode;
import com.walking.project.common.authorization.JWTToken;
import com.walking.project.dataobject.vo.UserVO;
import com.walking.project.mapper.UserMapper;
import com.walking.project.dataobject.entity.User;
import com.walking.project.service.UserService;
import com.walking.project.utils.JWTUtils;
import com.walking.project.utils.JedisUtils;
import com.walking.project.utils.MD5Encrypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static com.walking.project.common.ProjectConstant.*;
import static com.walking.project.common.authorization.SecurityConsts.*;


/**
* @Author: CNwalking
* @DateTime: 2020/04/15
* @Description:
*/
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private JedisUtils jedisUtils;

    @Override
    public Result isLoginSuccess(UserVO vo, HttpServletResponse response) {
        User user = userMapper.selectByAccount(vo.getAccount());
        String encryptedPswd = MD5Encrypt.md5AndKey(vo.getPassword(), MD5_SECRET_KEY);
        if (!encryptedPswd.equals(user.getPassword())) {
            return new Result(ResultCode.FAILED,"用户名或密码错误");
        }
        String currentTime = String.valueOf(System.currentTimeMillis());
        String token = JWTUtils.sign(vo.getAccount(), currentTime);
        String refreshTokenKey = PREFIX_SHIRO_CACHE + vo.getAccount();
        jedisUtils.saveString(refreshTokenKey, currentTime, TOKEN_EXPIRE_TIME / 1000);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new JWTToken(token);
        subject.login(authenticationToken);
        // token写入header中
        response.setHeader(REQUEST_AUTH_HEADER, token);
        response.setHeader("Access-Control-Expose-Headers", REQUEST_AUTH_HEADER);
        return new Result(ResultCode.SUCCESS, "登录成功");
    }
}
