package com.cutout.server.domain.bean.user;

import lombok.Data;

@Data
public class UserDownloadBean {

    /**
     * 剩余有效次数
     */
    private int valid_count;

    /**
     * 剩余有效时间
     */
    private int valid_time;

    /**
     * 购买/修改时间
     */
    private int update_time;
}
