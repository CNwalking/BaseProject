package com.walking.project.common.authorization;

import com.walking.project.service.UserService;
import com.walking.project.utils.JWTUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static com.walking.project.common.authorization.SecurityConsts.*;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/15 1:40 下午
 * @Description:流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private UserService userService;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 检测请求头里面有没有Authorization字段
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(REQUEST_AUTH_HEADER);
        return authorization != null;
    }

    /**
     * 最后再提交给realm进行登入
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(REQUEST_AUTH_HEADER);
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        String account = JWTUtil.getClaim(authorization, ACCOUNT);

        return true;
    }



}
