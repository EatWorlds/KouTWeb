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
        return userMongoModel.addUser(userInfoBean);
    }

    @Override
    public UserInfoBean getUserFromEmail(String email) {
        return userMongoModel.getUserFromEmail(email);
    }

    @Override
    public UserInfoBean getUserFromCode(String code) {
        return userMongoModel.getUserFromCode(code);
    }

    @Override
    public void updateUserFromCode(String code) {
        userMongoModel.updateUserFromCode(code);
    }

    @Override
    public void removeUser(String email) {
        userMongoModel.removeUser(email);
    }

    @Override
    public void cleanUserToken(String token) {
        userMongoModel.cleanUserToken(token);
    }
}
