package com.cutout.server.domain.bean.user;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * 存储用户token有效期的bean，存储在mongodb中
 */
@Data
public class UserTokenBean {

    @Id
    private String id;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 存储在缓存中的token
     */
    private String refresh_token;

    /**
     * 过期时间，时间戳表示
     */
    private int expire_time;
}
