package com.walking.project.common;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:03
 * @Description:
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public Result<String> APIExceptionHandler(APIException e) {
        // 注意哦，这里传递的响应码枚举
        return new Result<>(ResultCode.FAILED, e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 注意哦，这里传递的响应码枚举
        return new Result<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }
}
