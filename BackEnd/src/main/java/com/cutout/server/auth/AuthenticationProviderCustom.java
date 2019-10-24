package com.cutout.server.auth;

import com.alibaba.fastjson.JSON;
import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.UserInfoModel;
import com.cutout.server.service.AuthIgnore;
import com.cutout.server.service.UserInfoDetailService;
import com.cutout.server.service.UserService;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationProviderCustom implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(AuthenticationProviderCustom.class);

    @Autowired
    private UserInfoModel userInfoModel;

    /**
     * 注入我们自己定义的用户信息获取对象
     */
    @Autowired
    private UserInfoDetailService userInfoDetailService;

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private UserService userService;

    @Autowired
    private Bases bases;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String message = messageCodeStorage.success_code;
        Map<String,String> result = new HashMap<>();
        String username = authentication.getName();
        try {

            String password = (String)authentication.getCredentials();

            // 用户有效性校验
            userInfoModel.checkUserInfo(username,password);

            // 验证用户信息是否存在
//            UserInfoBean userInfoBean = userService.findUserByEmail(username);

            UserInfoBean userInfoBean = (UserInfoBean)userInfoDetailService.loadUserByUsername(username);
            logger.info("authenticate = " + JSON.toJSONString(userInfoBean));
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

            boolean isRight = userInfoModel.isPasswordRight(password,userInfoBean.getPassword());
            logger.info("isRight = " + isRight);

            // 验证密码是否正确
            if (!isRight) {
                throw new MessageException(messageCodeStorage.user_login_email_password_error);
            }

            // 创建token
            String token = jwtTokenUtil.generateToken(userInfoBean);

            result.put(ConstantConfigure.USER_TOKEN_KEY,token);
            result.put(ConstantConfigure.RESULT_EMAIL,username);
            result.put(ConstantConfigure.RESUTL_ID,userInfoBean.getId());

            userService.updateUserWithLogin(username,token);
        } catch (MessageException messageException) {
            message = messageException.getMessage();
            throw new AuthenticationServiceException(message);
        } catch (Exception e) {
            logger.error("",e);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, authentication.getCredentials());
        usernamePasswordAuthenticationToken.setDetails(result);
        return usernamePasswordAuthenticationToken;
    }

    /**
     * 执行支持判断
     *
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
