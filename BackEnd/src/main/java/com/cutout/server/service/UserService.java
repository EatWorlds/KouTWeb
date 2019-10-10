package com.cutout.server.service;

import com.cutout.server.domain.bean.user.UserInfoBean;

public interface UserService
{

    /**
     * 添加用户
     *
     * @param userInfoBean
     * @return
     */
    UserInfoBean addUser(UserInfoBean userInfoBean);

    /**
     * 根据邮箱获取用户信息
     * @param email
     * @return
     */
    UserInfoBean findUserByEmail(String email);

    /**
     * 根据验证码获取用户信息
     * @param code
     * @return
     */
    UserInfoBean findUserByCode(String code);

    /**
     *  根据code，更新用户激活状态
     * @param code
     */
    void updateUserByCode(String code);

    /**
     * 删除用户，注册失败的时候使用
     * @param email
     */
    void removeUser(String email);

    /**
     * 将用户token置空
     * @param token
     */
    void cleanUserToken(String token);

    /**
     * 用户登录之后，更新用户token和最后登录时间
     *
     * @param email
     * @param token
     */
    void updateUserWithLogin(String email,String token);
}
