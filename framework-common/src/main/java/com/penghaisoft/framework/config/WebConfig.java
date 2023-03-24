package com.penghaisoft.framework.config;

import com.penghaisoft.framework.interceptor.AuthorizationInterceptor;
import com.penghaisoft.framework.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * web集中配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{


    @Value("${framework.login-interceptor.exclude}")
    private String[] loginInterceptorExclude;

    @Value("${framework.authorization-interceptor.exclude}")
    private String[] authorizationInterceptorExclude;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Resource
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//      登录拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(Arrays.asList(loginInterceptorExclude));
//      权限拦截器
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**").excludePathPatterns(Arrays.asList(authorizationInterceptorExclude));
    }
}
