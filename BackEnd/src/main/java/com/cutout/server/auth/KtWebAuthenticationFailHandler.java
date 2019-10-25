package com.cutout.server.auth;

import com.cutout.server.utils.ResponseMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName KtWebAuthenticationFailHandler
 * @Description: 登录失败的处理
 * @Author xuyue
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
@Component
public class KtWebAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(KtWebAuthenticationFailHandler.class);

    @Autowired
    private ResponseMsgUtil responseMsgUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String result = exception.getMessage();
//        logger.info("onAuthenticationFailure " + result);
        responseMsgUtil.out(response,exception.getMessage());
    }
}
