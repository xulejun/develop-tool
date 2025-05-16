package com.legend.web.interceptor;

import com.legend.web.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.message.AuthException;

/**
 * 全局统一处理异常
 *
 * @author legend xu
 * @date 2022/1/14
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.legend.web.controller")
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error("404", "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Result.error("401", "数据库中已存在该记录");
    }

    @ExceptionHandler(AuthException.class)
    public Result handleAuthorizationException(AuthException e) {
        log.error(e.getMessage(), e);
        return Result.error("403", "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error("400","请求异常");
    }
}
