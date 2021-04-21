package com.kithy.webfluxsampleapp.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * In this example, our filters are registered by default for all the URL's in our application
 */
@Slf4j
@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("Logging Request {} : {}", request.getMethod(), request.getRequestURI());

        filterChain.doFilter(servletRequest, servletResponse);
        log.info("Logging Response : {}", response.getContentType());
    }
}
