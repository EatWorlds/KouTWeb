package com.cutout.server.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
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

    /**
     * 加上Bean注解，防止在拦截器中使用自动注入Autowired空指针的问题
     * @return
     */
    @Bean
    public HandlerInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    public HandlerInterceptor getAliPayInterceptor(){
        return new AliPayInterceptor();
    }

    @Bean
    public HandlerInterceptor getWxPayInterceptor(){
        return new WxPayInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        InterceptorRegistration addInterceptor = registry.addInterceptor(getLoginInterceptor());
        addInterceptor.addPathPatterns("/**");
        // 通过判断是否有AuthIgnore来过滤是否需要token校验
        addInterceptor.excludePathPatterns("/v1/aliPay/**")
                        .excludePathPatterns("/v1/wxPay/**");

        registry.addInterceptor(getAliPayInterceptor()).addPathPatterns("/v1/aliPay/**");
        registry.addInterceptor(getWxPayInterceptor()).addPathPatterns("/v1/wxPay/**");

    }
}
