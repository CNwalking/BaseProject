package com.walking.project.common;

import com.alibaba.fastjson.JSON;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:03
 * @Description:统一API响应结果封装
 */
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
