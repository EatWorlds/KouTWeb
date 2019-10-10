package com.cutout.server.service.impl;

import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.UserMongoModel;
import com.cutout.server.service.MallService;
import com.cutout.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMongoModel userMongoModel;

    @Autowired
    private MallService mallService;

    @Override
    public UserInfoBean addUser(UserInfoBean userInfoBean) {
        userInfoBean = userMongoModel.addUser(userInfoBean);
        if (userInfoBean != null) {
            mallService.sendHtmlMail(userInfoBean);
        }
        return userInfoBean;
    }

    @Override
    public UserInfoBean findUserByEmail(String email) {
        return userMongoModel.findUserByEmail(email);
    }

    @Override
    public UserInfoBean findUserByCode(String code) {
        return userMongoModel.findUserByCode(code);
    }

    @Override
    public void updateUserByCode(String code) {
        userMongoModel.updateUserByCode(code);
    }

    @Override
    public void removeUser(String email) {
        userMongoModel.removeUser(email);
    }

    @Override
    public void cleanUserToken(String token) {
        userMongoModel.cleanUserToken(token);
    }

    @Override
    public void updateUserWithLogin(String email, String token) {
        userMongoModel.updateUserWithLogin(email,token);
    }
}
