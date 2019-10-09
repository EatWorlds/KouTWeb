package com.cutout.server.configure.message;

import com.cutout.server.domain.bean.response.MessageCodeBean;
import com.cutout.server.utils.Bases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageCodeConfigure {

    @Autowired
    private Bases bases;

    private Map<String, MessageCodeBean> messageMap = new HashMap<>();

    /**
     * 获取消息体
     * @param messageKey
     * @return
     */
    public MessageCodeBean getMessageCodeBean(String messageKey){
        //否则从Storage中获取字符串进行组装成BEAN，然后返回
        String[] strings = bases.split(messageKey, "|");

        if(messageMap.containsKey(strings[0]))
            return messageMap.get(strings[0]);

        MessageCodeBean messageCodeBean = new MessageCodeBean();
        messageCodeBean.setMessageKey(strings[0]);
        messageCodeBean.setMessageCode(Integer.parseInt(strings[1]));
        messageCodeBean.setMessageDesc(strings[2]);
        messageMap.put(strings[0], messageCodeBean);
        return messageCodeBean;
    }
}
