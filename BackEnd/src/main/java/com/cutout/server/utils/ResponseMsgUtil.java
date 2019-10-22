package com.cutout.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class ResponseMsgUtil {

    private Logger logger = LoggerFactory.getLogger(ResponseHelperUtil.class);

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    /**
     * 在拦截器中发送错误信息
     *
     * @param response
     * @param messageKey
     */
    public void out(HttpServletResponse response, String messageKey) {
        out(response,messageKey,"");
    }

    public void out(HttpServletResponse response, String messageKey, Object data) {
        // ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            String json = new ObjectMapper().writeValueAsString(responseHelperUtil.returnMessage(messageKey,data));
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            logger.error("IO",e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("IO",e);
                }
            }
        }
    }
}
