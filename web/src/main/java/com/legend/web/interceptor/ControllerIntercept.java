package com.xlj.tools.interceptor;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * 通过aop 实现接口调用校验
 * 使用打开下面两个注解
 *
 * @author legend xu
 */
//@Component
//@Aspect
@Slf4j
public class ControllerIntercept {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerIntercept.class);

    @Autowired
    private HttpServletRequest request;

    //    @Pointcut("execution(public * com.legend.web..controller..*.*(..))")
    public void pointcut() {
    }

    //    @Around("pointcut()")
    public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
        String apiType = "";
        String userName = "NA";
        int code = HttpStatus.OK.value();
        String message = HttpStatus.OK.getReasonPhrase();
        try {
            apiType = pjp.getSignature().getName();
            // obj为"anonymousUser"时的权限校验
            String auth = request.getHeader("Authorization");
            if (auth == null || !auth.startsWith("Basic ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Auth Failure");
            }
            String authValue = new String(Base64.getDecoder().decode(auth.substring(6)));
            boolean isLigalUser = authValue.equals("自定义的鉴权码");
            if (!isLigalUser) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Auth Failure");
            }
            // 调用真实业务方法
            return pjp.proceed();
        } catch (Exception e) {
            LOGGER.error("process task error:{}, username:{}, apiType:{}", code, userName, apiType, e);
            // 参数null 可用通用response对象封装
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    //    @Pointcut("execution(public * com.legend.web..controller..*.*(..))")
    public void webLog() {
    }

    //    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        String url = "";
        String param = "";
        String body = "";
        try {
            // 获取 HttpServletRequest
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            // 用户
            String userName = String.valueOf(request.getAttribute("userName"));
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
            log.info("用户：{}，请求地址：{}，请求参数：{}，请求体：{}", userName, url, param, body);
        } catch (Exception e) {
            log.warn("AOP接口调用日志输出异常：url={}，param={}，body={}", url, param, body, e);
        }
    }

}