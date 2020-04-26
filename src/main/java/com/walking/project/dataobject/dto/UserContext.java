package com.walking.project.dataobject.dto;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/15 3:38 下午
 * @Description: 业务中要拿account的时候可以用UserContext.getCurrentUser().getAccount()
 */
public class UserContext implements AutoCloseable{
    static final ThreadLocal<LoginUser> CURRENT = new ThreadLocal<>();

    public UserContext(LoginUser user) {
        CURRENT.set(user);
    }

    public static LoginUser getCurrentUser() {
        return CURRENT.get();
    }

    @Override
    public void close() throws Exception {
        CURRENT.remove();
    }
}
