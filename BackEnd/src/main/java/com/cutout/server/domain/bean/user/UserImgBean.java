package com.cutout.server.domain.bean.user;

import lombok.Data;

/**
 * @ClassName UserImgBean
 * @Description: 文件上传信息
 * @Author Dimple
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
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
