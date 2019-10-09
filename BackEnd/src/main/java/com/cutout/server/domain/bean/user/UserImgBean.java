package com.cutout.server.domain.bean.user;

import lombok.Data;

/**
 *  文件上传信息
 */
@Data
public class UserImgBean {

    /**
     * 文件名
     */
    private String file_name;

    /**
     *  文件路径
     */
    private String img_url;

    /**
     * 创建时间
     */
    private int create_time;
}
