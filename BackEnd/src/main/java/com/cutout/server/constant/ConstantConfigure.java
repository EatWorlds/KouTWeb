package com.cutout.server.constant;

import org.springframework.stereotype.Component;

@Component
public class ConstantConfigure {

    public final static String RESULT_EMAIL = "email";

    public final static String USER_TOKEN_KEY = "token";

    /**
     * 1分钟的时间
     */
    public final static int ONE_MINUTE_TIMES = 60;

    /**
     * 5分钟的时间
     */
    public final static int FIVE_MINUTES_TIMES = 5 * 60;

    /**
     * 24小时的时间
     */
    public final static int TWENTY_FOUR_HOURS_TIMES = 60 * 60 * 24;

    // jwt过期时间15分钟
    public final static long JWT_EXPIRE_TIME = 15 * 60 * 1000;

    // token失效时间
    public final static long TOKEN_INVALID_TIME = 24 * 60;

    public final static String USER_ATTRIBUTE_KEY = "user_attribute_key";
}
