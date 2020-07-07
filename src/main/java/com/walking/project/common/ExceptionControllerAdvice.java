package com.walking.project.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:03
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    @ResponseBody
    public Result<String> APIExceptionHandler(APIException e) {
        log.error("业务代码里出问题了兄dei", e);
        return new Result<>(ResultCode.FAILED, e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new Result<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }

    /**
     * 给整体的Exception一个返回
     * @param e Exception大类
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        log.error("出现非业务代码中的问题", e);
        if (e instanceof ServletRequestBindingException) {
            return new Result<>(ResultCode.FAILED, "url绑定路由问题");
        } else if (e instanceof NoHandlerFoundException) {
            return new Result<>(ResultCode.FAILED, "没有找到对应的访问路径");
        } else {
            return new Result<>(ResultCode.FAILED, e.getMessage());
        }
    }
}
