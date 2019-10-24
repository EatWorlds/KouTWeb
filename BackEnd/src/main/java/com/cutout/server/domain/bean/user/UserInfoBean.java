package com.cutout.server.domain.bean.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Document
public class UserInfoBean implements UserDetails {

    @Id
    private String id;

    /**
     * 密码
     */
    private String password;

    /**
     * 最近登录时间
     */
    private int last_login;

    /**
     * token
     */
    private String token;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private int create_time;

    /**
     * 消费金额
     */
    private int total_cost;

    /**
     * 状态 0：注册未激活，1：注册且激活
     */
    private int status;

    /**
     * 利用UUID生成一段数字，发动到用户邮箱，当用户点击链接时
     * 在做一个校验如果用户传来的code跟我们发生的code一致，更改状态为“1”来激活用户
     */
    private String code;

    /**
     * 激活链接发送的时间，24小时过期，需要重新发送
     */
    private int code_time;

    /**
     * 上传的图片列表
     */
    private List<UserImgBean> userImgs;

    /**
     * 下载有效性，剩余有效次数、时间等
     */
    private UserDownloadBean userDownload;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
