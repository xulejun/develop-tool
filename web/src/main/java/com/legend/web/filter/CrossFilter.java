package com.legend.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 过滤器 解决 跨域问题（也可使用注解，无法动态地址）
 *
 * @author legend xu
 * @date 2023/6/29
 */
@Component
public class CrossFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 将ServletResponse转换为HttpServletResponse
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 如果不是80端口,需要将端口加上,如果是集群,则用Nginx的地址,同理不是80端口要加上端口
        String[] allowDomain = {"http://www.baidu.com", "http://123.456.789.10", "http://123.16.12.23:8080"};
        Set allowedOrigins = new HashSet(Arrays.asList(allowDomain));
        String originHeader = ((HttpServletRequest) request).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {
            httpResponse.setHeader("Access-Control-Allow-Origin", originHeader);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
            // 如果要把Cookie发到服务器，需要指定Access-Control-Allow-Credentials字段为true
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Expose-Headers", "*");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
