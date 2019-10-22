package com.cutout.server.configure.exception;

import lombok.Data;

/**
 * 2019-09-20
 */

public class MessageException extends Exception {

    private static final long serialVersionUID = 6917398533139941801L;

    private String message;

    private int code;

    public MessageException(String message) {
        super();
        this.message = message;
    }

    public MessageException(String message,int code) {
        super();
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
