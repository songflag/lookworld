package com.sinker.lookworld.config;


import com.sinker.lookworld.common.SecurityHandlerInterceptor;
import com.sinker.lookworld.common.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.config
 * Author: sinker
 * CreateTime: 2025-03-10  15:52
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private UserLoginInterceptor userLoginInterceptor;
    @Autowired
    public void setUserLoginInterceptor(UserLoginInterceptor userLoginInterceptor) {
        this.userLoginInterceptor = userLoginInterceptor;
    }
    private SecurityHandlerInterceptor securityHandlerInterceptor;
    @Autowired
    public void setSecurityHandlerInterceptor(SecurityHandlerInterceptor securityHandlerInterceptor) {
        this.securityHandlerInterceptor = securityHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //sin 登录拦截器
        registry.addInterceptor(userLoginInterceptor).addPathPatterns("/api/**/*").excludePathPatterns("/api/login/**", "/api/upload/**");
        //sin 权限拦截器
        registry.addInterceptor(securityHandlerInterceptor).addPathPatterns("/api/**/*").excludePathPatterns("/api/login/**", "/api/upload/**");
    }

}
