package com.cutout.server.service.impl;

import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.service.MallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MallServiceImpl implements MallService {

    private Logger logger = LoggerFactory.getLogger(MallServiceImpl.class);

    @Override
    public void sendHtmlMail(UserInfoBean userInfoBean) {
        String to = userInfoBean.getEmail();
        String subject = "注册验证";

        String content = "<html><head></head><body><h1>这是一封激活邮件,激活请点击以下链接</h1><h3><a href='http://localhost:9099/v1/user/"
                + userInfoBean.getCode() + "'>http://localhost:9099/v1/user/" + userInfoBean.getCode()
                + "</href></h3></body></html>";

        MailUtil.send(to,subject,content,true);
    }
}
