package com.cutout.server.service;

import com.cutout.server.domain.bean.user.UserInfoBean;

public interface MallService {

    /**
     * 发送HTML邮件，方便用户点击附带的code用来验证激活账户
     * @param userInfoBean
     */
    void sendHtmlMail(UserInfoBean userInfoBean);
}
