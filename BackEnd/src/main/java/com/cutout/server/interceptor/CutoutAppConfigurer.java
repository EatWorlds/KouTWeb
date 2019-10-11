package com.cutout.server.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 2019-09-17
 *
 * @author dimple
 *
 *
 */
@Configuration
public class CutoutAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(new LoginInterceptor());
        addInterceptor.addPathPatterns("/**");
        // 过滤登录
        addInterceptor.excludePathPatterns("/v1/user/login")
                        .excludePathPatterns("/v1/user");
    }
}
