package com.novel.user.config;

import com.novel.user.interceptor.TokenInterceptorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 昴星
 * @date 2023-09-07 19:46
 * @explain
 */

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptorHandler tokenInterceptorHandler;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptorHandler).
                addPathPatterns("/**");
    }
}
