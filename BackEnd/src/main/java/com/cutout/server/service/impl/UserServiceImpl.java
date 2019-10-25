package com.cutout.server.service.impl;

import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.UserMongoModel;
import com.cutout.server.service.MailService;
import com.cutout.server.service.UserService;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMongoModel userMongoModel;

    @Autowired
    private UUIDUtil uuidUtil;

    @Autowired
    private Bases bases;

    @Override
    public UserInfoBean addUser(UserInfoBean userInfoBean) {
        userInfoBean = userMongoModel.addUser(userInfoBean);
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

    @Override
    public UserInfoBean updateUserCodeByEmail(UserInfoBean userInfoBean) {
        userInfoBean.setCode(uuidUtil.getUUIDCode());
        userInfoBean.setCode_time(bases.getSystemSeconds());
        userMongoModel.updateUserCodeByEmail(userInfoBean);
        return userInfoBean;
    }

    @Override
    public UserInfoBean findUserById(String id) {
        return userMongoModel.findUserById(id);
    }
}
