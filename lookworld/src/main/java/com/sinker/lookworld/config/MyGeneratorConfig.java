package com.sinker.lookworld.config;



import com.sinker.lookworld.common.UserLoginInterceptor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.config
 * Author: sinker
 * CreateTime: 2025-03-06  20:58
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Configuration
public class MyGeneratorConfig implements WebMvcConfigurer {

    //配置类，配置spring二级缓存时，获得的key
    @Bean
    public KeyGenerator myKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getClass()+Arrays.deepToString(params);
            }
        };
    }

}
