package com.cutout.server.domain.bean.response;

import lombok.Data;

@Data
public class MessageCodeBean {

    private int messageCode;

    private String messageKey;

    private String messageDesc;

    private String messageGroup;
}
