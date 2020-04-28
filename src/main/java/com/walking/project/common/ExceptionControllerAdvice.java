package com.walking.project.common;

import com.walking.project.common.exception.APIException;
import com.walking.project.common.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:03
 * @Description: Exception的统一抛出构造
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    @ResponseBody
    public Result<String> APIExceptionHandler(APIException e) {
        log.error("APIException ", e);
        return new Result<>(ResultCode.FAILED, e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException ", e);
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new Result<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }

    @ExceptionHandler(ShiroException.class)
    public Result<String> ShiroExceptionHandler(ShiroException e) {
        log.error("ShiroException ", e);
        return new Result<>(ResultCode.FAILED, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result<String> UnauthorizedExceptionHandler(UnauthorizedException e) {
        log.error("UnauthorizedException ", e);
        return new Result<>(ResultCode.FAILED, e.getMessage());
    }

//    // Exception是统一的处理，一般不直接这样，而是分开成具体的Exception来进行定制化操作
//    @ExceptionHandler(Exception.class)
//    public Result<String> ExceptionHandler(Exception e) {
//        return new Result<>(ResultCode.VALIDATE_FAILED, e.getMessage());
//    }
}
