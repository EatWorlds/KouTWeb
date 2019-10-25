package com.cutout.server;

import com.cutout.server.auth.AuthenticationProviderCustom;
import com.cutout.server.auth.KtWebAuthenticationFailHandler;
import com.cutout.server.auth.KtWebAuthenticationSuccessHandler;
import com.cutout.server.service.UserInfoDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;

/**
 *
 * https://woodwhales.github.io/2019/04/12/026/
 *
 * https://www.cnblogs.com/zbjj-itblog/p/10730619.html
 *
 * https://www.jianshu.com/p/c159afb7bd4a
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    UserInfoDetailService userInfoDetailService;

    @Autowired
    private AuthenticationProviderCustom providerCustom;

    @Autowired
    private KtWebAuthenticationSuccessHandler ktWebAuthenticationSuccessHandler;

    @Autowired
    private KtWebAuthenticationFailHandler ktWebAuthenticationFailHandler;

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        https://blog.csdn.net/qq_35494808/article/details/82998135 解决跨域问题
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin()
                // 重定向login页面
                .loginProcessingUrl("/v1/user/login")
                // 登录成功返回
                .successHandler(ktWebAuthenticationSuccessHandler)
                // 登录失败返回
                .failureHandler(ktWebAuthenticationFailHandler);
//                .and()
//                .cors();// 跨域问题 https://blog.csdn.net/w903328615/article/details/80503750
//        http.csrf().disable();
    }

    /**
     * 配置一个userDetailsService Bean
     *
     * 不再默认生成security.user 用户
     * @return
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return userInfoDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(providerCustom);
    }

}
