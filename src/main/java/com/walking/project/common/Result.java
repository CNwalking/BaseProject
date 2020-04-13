package com.walking.project.common;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:03
 * @Description:统一API响应结果封装
 */
@Getter
@ApiModel
public class Result<T> {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "响应message")
    private String msg;
    @ApiModelProperty(value = "响应数据")
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
