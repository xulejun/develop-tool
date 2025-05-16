package com.legend.web.interceptor;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author 602
 * @date 2025/5/16
 */
@Slf4j
@Aspect
@Component
public class CommonAspect {

    @Pointcut("execution(public * com.legend.web.controller..*.*(..))")
    public void apiPointcut() {
    }

    // 记录请求参数及响应结果
    @Around("apiPointcut()")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String url = "";
        String param = "";
        String body = "";
        Object result = null;
        try {
            // 获取 HttpServletRequest
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }
            HttpServletRequest request = attributes.getRequest();
            // 请求地址
            url = request.getRequestURL().toString();
            // 请求参数
            Map<String, String[]> paramMap = request.getParameterMap();
            if (!paramMap.isEmpty()) {
                param = JSON.toJSONString(paramMap);
            }
            // 请求体
            Object[] args = joinPoint.getArgs();

            for (Object arg : args) {
                if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                    continue;
                }
                body = JSON.toJSONString(arg);
            }
            result = joinPoint.proceed();
            log.info(MessageFormat.format("请求地址={0}，请求参数={1}，请求体={2}，响应={3}", url, param, body, result));
            return result;
        } catch (Exception e) {
            log.error(MessageFormat.format("接口请求异常，请求地址={0}，请求参数={1}，请求体={2}，响应={3}，e={4}", url, param, body, result, e));
            throw e;
        }
    }

    @AfterThrowing(pointcut = "apiPointcut()", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        log.error("接口 {} 发生异常: {}", method.getName(), ex.getMessage());
        // 可在此处发送邮件或记录到数据库（网页1][6](@ref)[^8）
    }
}
