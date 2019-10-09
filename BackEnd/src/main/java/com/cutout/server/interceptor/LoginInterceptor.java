package com.cutout.server.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("==============================");
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie:
//             cookies) {
//            logger.info("cookie = " + cookie.getName());
//            logger.info("cookie = " + cookie.getValue());
//            logger.info("cookie = " + cookie.getDomain());
//            logger.info("cookie = " + cookie.getPath());
//            logger.info("cookie = " + cookie.getComment());
//            logger.info("cookie = " + cookie.getMaxAge());
//            logger.info("cookie = " + cookie.getSecure());
//            logger.info("cookie = " + cookie.getVersion());
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
