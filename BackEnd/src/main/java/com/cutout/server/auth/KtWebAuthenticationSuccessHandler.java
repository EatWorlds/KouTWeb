package com.cutout.server.auth;

import com.alibaba.fastjson.JSON;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.utils.ResponseMsgUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class KtWebAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ResponseMsgUtil responseMsgUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object info = (Object) authentication.getDetails();
        logger.info("onAuthenticationSuccess" + JSON.toJSONString(info));
        responseMsgUtil.out(response,messageCodeStorage.success_code,authentication.getDetails());
    }
}
