package com.cutout.server.service;

import com.alibaba.fastjson.JSON;
import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.domain.bean.user.UserInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDetailService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserInfoDetailService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername username ================= " + username);
        UserInfoBean userInfoBean = userService.findUserByEmail(username);
        logger.info("result ==" + JSON.toJSONString(userInfoBean));
//        if (userInfoBean == null) {
//            throw new MessageException(messageCodeStorage.user_not_exists_error);
//        }
        return userInfoBean;
    }
}
