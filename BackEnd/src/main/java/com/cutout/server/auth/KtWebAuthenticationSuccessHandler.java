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

/**
 * @ClassName KtWebAuthenticationSuccessHandler
 * @Description: 登录成功的处理
 * @Author Dimple
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
@Component
public class KtWebAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ResponseMsgUtil responseMsgUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object info = (Object) authentication.getDetails();
//        logger.info("onAuthenticationSuccess" + JSON.toJSONString(info));
        responseMsgUtil.out(response,MessageCodeStorage.success_code,authentication.getDetails());
    }
}
