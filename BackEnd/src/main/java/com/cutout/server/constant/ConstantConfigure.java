package com.cutout.server.constant;

import org.springframework.stereotype.Component;

@Component
public class ConstantConfigure {

    public final static String RESULT_EMAIL = "email";

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
}
