package com.cutout.server.service.impl;

import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.domain.bean.user.UserVerityCodeBean;
import com.cutout.server.model.UserMongoModel;
import com.cutout.server.model.UserVerityCodeModel;
import com.cutout.server.service.MailService;
import com.cutout.server.service.UserService;
import com.cutout.server.service.VerifiedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerityCodeServiceImpl implements VerifiedCodeService {

    @Autowired
    private UserVerityCodeModel userVerityCodeModel;

    @Override
    public UserVerityCodeBean addVerityCode(String email) {
        return userVerityCodeModel.addVerityCode(email);
    }

    @Override
    public UserVerityCodeBean findVerityCodeByEmail(String email) {
        return userVerityCodeModel.findVerityCodeByEmail(email);
    }

    @Override
    public UserVerityCodeBean updateVerityCodeByEmail(UserVerityCodeBean userVerityCodeBean) {
        return userVerityCodeModel.updateVerityCodeByEmail(userVerityCodeBean);
    }
}
