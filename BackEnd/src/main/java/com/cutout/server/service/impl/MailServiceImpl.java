package com.cutout.server.service.impl;

import cn.hutool.extra.mail.MailUtil;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.domain.bean.user.UserVerityCodeBean;
import com.cutout.server.service.MailService;
import com.cutout.server.utils.Bases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl implements MailService {

    private Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private Bases bases;

    @Override
    public void sendHtmlMail(UserInfoBean userInfoBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String to = userInfoBean.getEmail();
                String subject = "注册验证";

                String content = "<html><head></head><body><h1>这是一封激活邮件（24小时有效），激活请点击以下链接</h1><h3><a href='http://localhost:9099/v1/user/"
                        + userInfoBean.getCode() + "'>http://61.160.212.32:49099/v1/user/" + userInfoBean.getCode()
                        + "</href></h3></body></html>";

                MailUtil.send(to,subject,content,true);
            }
        }).start();

    }

    @Override
    public void sendVerityCode(UserVerityCodeBean userVerityCodeBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String to = userVerityCodeBean.getEmail();

                String subject = "您的校验码";

                StringBuilder stringBuilder = new StringBuilder();
                String content = stringBuilder.append("您的校验码是：")
                                                .append(userVerityCodeBean.getVerity_code())
                                                .append("。")
                                                .append("该验证码五分钟内有效，请尽快使用！")
                                                .toString();
                MailUtil.send(to,subject,content,false);
            }
        }).start();
    }
}
