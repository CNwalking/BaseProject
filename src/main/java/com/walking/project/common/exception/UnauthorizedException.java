package com.walking.project.common.exception;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/23 5:51 下午
 * @Description: 简单重写一下shiro本身自带的UnauthorizedException
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
