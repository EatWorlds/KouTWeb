package com.cutout.server.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.service.AuthIgnore;
import com.cutout.server.service.UserService;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.JwtTokenUtil;
import com.cutout.server.utils.ResponseHelperUtil;
import com.cutout.server.utils.ResponseMsgUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private Bases bases;

    @Autowired
    private ResponseMsgUtil responseMsgUtil;

    private static final String HEADER_STRING = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod)handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        // 如果有@AuthIgnore注解，则不验证token
        if (annotation != null) {
            return true;
        }

        String token = request.getHeader(ConstantConfigure.USER_TOKEN_KEY);
        logger.info("LoginInterceptor preHandle USER_TOKEN_KEY token = " + token);
        String authorization = request.getHeader(HEADER_STRING);
        logger.info("LoginInterceptor preHandle HEADER_STRING authorization = " + authorization);
        String tokenNew = authorization.replace(TOKEN_PREFIX,"");

//        String token1 = request.getHeader()
        logger.info("LoginInterceptor preHandle token = " + token);
        logger.info("LoginInterceptor preHandle authorization tokenNew = " + tokenNew);
        token = tokenNew.trim();
        logger.info("LoginInterceptor preHandle token trim = " + token);
        if (StringUtils.isEmpty(token)) {
            responseMsgUtil.out(response,MessageCodeStorage.user_token_invalid);
            return false;
        }

        // 从token中获取用户邮箱信息
        String userEmail = JWT.decode(token).getAudience().get(0);
        logger.info("LoginInterceptor preHandle userEmail = " + userEmail);
        // 根据用户邮箱信息获取用户信息
        UserInfoBean userInfoBean = userService.findUserByEmail(userEmail);
        logger.info("LoginInterceptor userInfoBean = " + JSON.toJSONString(userInfoBean));
        if (userInfoBean == null) {
            responseMsgUtil.out(response,MessageCodeStorage.user_not_exists_error);
            return false;
        }

        if (StringUtils.isEmpty(userInfoBean.getToken())) {
            responseMsgUtil.out(response,MessageCodeStorage.user_not_login_error);
            return false;
        }

        // 如果传递的token和数据库的token对不上，说明已经重新登录过了
        if (!userInfoBean.getToken().equals(token)) {
            responseMsgUtil.out(response,MessageCodeStorage.user_token_invalid);
            return false;
        }

        // 如果当前时间和最后一次登录时间超过24小时，则提示登录已过期
        if (bases.getSystemSeconds() - userInfoBean.getLast_login() > ConstantConfigure.TOKEN_INVALID_TIME) {
            // 需要删除token
            userService.cleanUserToken(token);
            responseMsgUtil.out(response,MessageCodeStorage.user_token_out_of_time);
            return false;
        }

        // 解析token
        Map<String,Object> result = jwtTokenUtil.verify(token,userInfoBean);
        request.setAttribute(ConstantConfigure.USER_ATTRIBUTE_KEY,result);

//        Map<String,Object> result = jwtTokenUtil.verify(token,userInfoBean);
//        // Mon Sep 23 17:46:33 CST 2019
//        Date time = (Date)result.get("time");
//        logger.info("time = " + (time.getTime()/1000));
//
//        logger.info(JSON.toJSONString(result));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
