package com.walking.project.common.authorization;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.walking.project.common.ProjectConstant;
import com.walking.project.dataobject.dto.LoginUser;
import com.walking.project.dataobject.dto.UserContext;
import com.walking.project.service.CacheService;
import com.walking.project.service.UserService;
import com.walking.project.utils.JWTUtils;
import com.walking.project.utils.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.walking.project.common.authorization.SecurityConsts.*;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/15 1:40 下午
 * @Description:流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private UserService userService;
    private CacheService cacheService;
    private JedisUtils jedisUtils;

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                responseError(request, response);
            }
        }
        return true;
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
        String account = JWTUtils.getAccount(authorization);
        UserContext userContext = new UserContext(new LoginUser(account));
        // 检查是否需要更换token，需要则重新颁发
        this.refreshTokenIfNeed(account, authorization, response);
        return true;
    }


    /**
     * 将非法请求跳转到 /401
     */
    private void responseError(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/401");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 检查是否需要更新Token
     *
     * @param authorization
     * @param currentTimeMillis
     * @return
     */
    private boolean refreshCheck(String authorization, Long currentTimeMillis) {
        String tokenMillis = JWTUtils.getTime(authorization);
        if (currentTimeMillis - Long.parseLong(tokenMillis) > (REFRESH_CHECK_TIME)) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否需要,若需要则校验时间戳，刷新Token，并更新时间戳
     * @param account
     * @param authorization
     * @param response
     * @return
     */
    private boolean refreshTokenIfNeed(String account, String authorization, ServletResponse response) {
        Long currentTimeMillis = System.currentTimeMillis();
        //检查刷新规则
        if (this.refreshCheck(authorization, currentTimeMillis)) {
            String lockName = PREFIX_SHIRO_CACHE + account;
            boolean b = cacheService.getLock(lockName, ProjectConstant.ExpireTime.ONE_MINUTE);
            if (b) {
                //获取到锁
                String refreshTokenKey= PREFIX_SHIRO_CACHE + account;
                if(jedisUtils.exists(refreshTokenKey)){
                    //检查redis中的时间戳与token的时间戳是否一致
                    String tokenTimeStamp = jedisUtils.get(refreshTokenKey);
                    String tokenMillis= JWTUtils.getTime(authorization);
                    if(!tokenMillis.equals(tokenTimeStamp)){
                        throw new TokenExpiredException(String.format("账户%s的令牌无效", account));
                    }
                }
                //时间戳一致，则颁发新的令牌
                log.info(String.format("为账户%s颁发新的令牌", account));
                String strCurrentTimeMillis = String.valueOf(currentTimeMillis);
                String newToken = JWTUtils.sign(account,strCurrentTimeMillis);

                //更新缓存中的token时间戳
                jedisUtils.saveString(refreshTokenKey, strCurrentTimeMillis, TOKEN_EXPIRE_TIME/1000);

                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader(REQUEST_AUTH_HEADER, newToken);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", REQUEST_AUTH_HEADER);
            }
            cacheService.releaseLock(lockName);
        }
        return true;
    }

}
