package com.cutout.server.utils;

import com.cutout.server.configure.message.MessageCodeConfigure;
import com.cutout.server.domain.bean.response.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ResponseHelperUtil {

    @Autowired
    private MessageCodeConfigure messageCodeConfigure;

    public ResponseBean getResponseBean(String messageKey) {
        return new ResponseBean(messageCodeConfigure.getMessageCodeBean(messageKey));
    }

    public ResponseBean returnMessage(String errorInfo, String errorData) {
        ResponseBean responseBean = getResponseBean(errorInfo);
        if (!StringUtils.isEmpty(errorData))
            responseBean.setData(errorData);
        return responseBean;
    }

    public ResponseBean returnMessage(String errorInfo) {
        ResponseBean responseBean = getResponseBean(errorInfo);
        return responseBean;
    }

    public ResponseBean returnMessage(String errorInfo, Object object) {
        ResponseBean responseBean = getResponseBean(errorInfo);
        responseBean.setData(object);
        return responseBean;
    }
}
