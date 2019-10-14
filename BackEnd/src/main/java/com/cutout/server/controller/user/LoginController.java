package com.cutout.server.controller.user;


import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.UserInfoModel;
import com.cutout.server.service.AuthIgnore;
import com.cutout.server.service.UserService;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.JwtTokenUtil;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * 2019-09-16
 *
 * @author dimple
 *
 * @RestController == @Controller + @ResponseBody，需要注意的是使用这个注解代表着整个类都是如此
 *
 * 当然 @Controller & @ResponseBody 还是可以使用的
 *
 */
@RestController
@RequestMapping("/v1")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private Bases bases;

    @Autowired
    private UserInfoModel userInfoModel;

    /**
     * 用户登录，返回用户个人信息
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @AuthIgnore
    public ResponseBean login(@RequestParam String username, @RequestParam String password) {

        String message = messageCodeStorage.success_code;
        Map<String,String> result = new HashMap<>();
        try {

            // 用户有效性校验
            userInfoModel.checkUserInfo(username,password);

            // 验证用户信息是否存在
            UserInfoBean userInfoBean = userService.findUserByEmail(username);
            logger.info("result ==" + userInfoBean);
            if (userInfoBean == null) {
                throw new MessageException(messageCodeStorage.user_not_exists_error);
            }

            // token已存在，则表示用户已经登录
            if (!StringUtils.isEmpty(userInfoBean.getToken())) {
                // 如果来登录的用户距离上一次超过固定的时间，则给他重新登录的机会，否则返回已经登录
                if (bases.getSystemSeconds() - userInfoBean.getLast_login() > ConstantConfigure.TOKEN_INVALID_TIME) {
                    userService.cleanUserToken(userInfoBean.getToken());
                } else {
                    throw new MessageException(messageCodeStorage.user_already_login_error);
                }
            }

            // 验证密码是否正确
            if (!userInfoModel.isPasswordRight(password,userInfoBean.getPassword())) {
                throw new MessageException(messageCodeStorage.user_login_email_password_error);
            }

            // 创建token
            String token = jwtTokenUtil.generateToken(userInfoBean);

            result.put("token",token);
            result.put("email",username);

            userService.updateUserWithLogin(username,token);
//            Map<String,Object> result = jwtTokenUtil.verify(token,userInfoBean);
//            // Mon Sep 23 17:46:33 CST 2019
//            Date time = (Date)result.get("time");
//            logger.info("time = " + (time.getTime()/1000));
//
//            logger.info(JSON.toJSONString(result));
        }catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {
            logger.error("LoginController login",e);
        }

        return responseHelperUtil.returnMessage(message,result);
    }

    /**
     * 用户退出
     *
     * @param email
     *
     * @return
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResponseBean logout(@RequestParam String email) {
        String message = messageCodeStorage.success_code;
        Map<String,String> result = new HashMap<>();
        try {
            logger.info("logout = " + email);

            // 用户有效性校验
            userInfoModel.checkUserEmail(email);

            // 验证用户信息是否存在
            UserInfoBean userInfoBean = userService.findUserByEmail(email);
            logger.info("logout userInfoBean ==" + userInfoBean);
            if (userInfoBean == null) {
                throw new MessageException(messageCodeStorage.user_not_exists_error);
            }

            // token不存在，则用户未登录
            String token = userInfoBean.getToken();
            if (StringUtils.isEmpty(token)) {
                throw new MessageException(messageCodeStorage.user_not_login_error);
            }

            userService.cleanUserToken(token);

            result.put(ConstantConfigure.RESULT_EMAIL,email);
        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {
            logger.error("LoginController logout",e);
        }
        return responseHelperUtil.returnMessage(message,result);
    }

}
