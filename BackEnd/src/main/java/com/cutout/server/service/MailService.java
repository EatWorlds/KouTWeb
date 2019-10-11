package com.cutout.server.service;

import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.domain.bean.user.UserVerityCodeBean;

public interface MailService {

    /**
     * 发送HTML邮件，方便用户点击附带的code用来验证激活账户
     * @param userInfoBean
     */
    void sendHtmlMail(UserInfoBean userInfoBean);

    /**
     * 注册/忘记密码，发送验证码到邮箱
     * @param userVerityCodeBean
     */
    void sendVerityCode(UserVerityCodeBean userVerityCodeBean);
}
