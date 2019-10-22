package com.cutout.server.domain.bean.response;

import lombok.Data;

@Data
public class ResponseBean {

    private int status = 1;

    private String msg = "请求成功";

    private Object data;

    public ResponseBean() {

    }

    public ResponseBean(MessageCodeBean messageCodeBean) {
        if (messageCodeBean == null) {
            return;
        }

        this.status = messageCodeBean.getMessageCode();
        this.msg = messageCodeBean.getMessageDesc();
    }

}
