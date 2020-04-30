package com.walking.project.common;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:03
 * @Description:
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    @ResponseBody
    public Result<String> APIExceptionHandler(APIException e) {
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
    public Result<String> ExceptionHandler(Exception e) {
        return new Result<>(ResultCode.VALIDATE_FAILED, e.getMessage());
    }
}
