package com.cutout.server.domain.bean.user;

import lombok.Data;

/**
 * 记录用户验证码的数据表
 */
@Data
public class UserVerityCodeBean {

    /**
     * 邮箱账号，注册/忘记密码都用
     */
    private String email;

    /**
     * 验证码
     */
    private int verity_code;

    /**
     * 更新时间
     */
    private int update_time;
}
