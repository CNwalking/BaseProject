package com.walking.project.common.exception;

import lombok.Getter;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:10
 * @Description:
 */
@Getter
public class APIException extends RuntimeException {
    private int code;
    private String msg;

    public APIException() {
        this(400, "接口错误");
    }

    public APIException(String msg) {
        this(400, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}