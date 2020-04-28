package com.walking.project.common.authorization;

import com.walking.project.common.annotation.CurrentUser;
import com.walking.project.common.exception.UnauthorizedException;
import com.walking.project.dataobject.dto.UserContext;
import com.walking.project.dataobject.entity.User;
import com.walking.project.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * @Author: CNwalking
 * @DateTime: 2020/4/26 4:12 下午
 * @Description: 把当前用户塞到方法里面
 */
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(User.class)
                && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String account = UserContext.getCurrentUser().getAccount();
        if (StringUtils.isNotBlank(account)) {
            User user = userMapper.selectByAccount(account);
            return user;
        }
        throw new UnauthorizedException("获取用户信息失败啦");

    }
}
