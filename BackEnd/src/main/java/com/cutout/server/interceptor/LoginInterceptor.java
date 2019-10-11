package com.cutout.server.interceptor;

import com.cutout.server.service.AuthIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String header = request.getHeader("token");
        logger.info("header = " + header);
        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod)handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            logger.info("====");
            return true;
        }

        // 如果有@AuthIgnore注解，则不验证token
        if (annotation != null) {
            return true;
        }

//        boolean isAssignableFromHandlerMethod = handler.getClass().isAssignableFrom(HandlerMethod.class);
//        logger.info("当前方法是否存在注解：" + isAssignableFromHandlerMethod);
//
//        if (isAssignableFromHandlerMethod) {
//            LoginRequired accessControl = ((HandlerMethod)handler).getMethodAnnotation(LoginRequired.class);
//            logger.info("当前方法的注解是:" + (accessControl != null ? accessControl.onlyLoginCanAccess() : ""));
//
//            if (accessControl == null || accessControl.onlyLoginCanAccess()) {
//                logger.info("已登录");
//            } else {
//                logger.info("未登录");
//            }
//        }


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
