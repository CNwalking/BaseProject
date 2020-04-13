package com.walking.project.common;

import lombok.Getter;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:08
 * @Description:
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),

    FAILED(400, "响应失败"),

    VALIDATE_FAILED(401, "参数校验失败"),

    ERROR(500, "未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}