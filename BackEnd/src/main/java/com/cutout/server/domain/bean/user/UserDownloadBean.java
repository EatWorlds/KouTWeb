package com.cutout.server.domain.bean.user;

import lombok.Data;

/**
 * @ClassName UserDownloadBean
 * @Description: 用户图片下载信息
 * @Author Dimple
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
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
