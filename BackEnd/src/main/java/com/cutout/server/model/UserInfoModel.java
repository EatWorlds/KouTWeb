package com.cutout.server.model;

import com.alibaba.fastjson.JSON;
import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.domain.bean.user.UserVerityCodeBean;
import com.cutout.server.service.UserService;
import com.cutout.server.service.VerifiedCodeService;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.UUIDUtil;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class UserInfoModel {

    private Logger logger = LoggerFactory.getLogger(UserInfoModel.class);

    @Autowired
    private Bases bases;

    @Autowired
    private VerifiedCodeService verifiedCodeService;

    @Autowired
    private UserService userService;

    /**
     * 校验邮箱，密码是否有效
     *
     * @param email
     * @param password
     * @throws MessageException
     */
    public void checkUserInfo(String email,String password) throws MessageException {

        // 验证邮箱和密码是否为空
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new MessageException(MessageCodeStorage.user_email_password_empty);
        }

        // 判断邮箱格式是否正确
        boolean isEmail = bases.isEmail(email);
        if (!isEmail) {
            throw new MessageException(MessageCodeStorage.user_email_invalid);
        }

    }

    /**
     * 校验验证码是否有效
     * @param code
     * @throws MessageException
     */
    public void checkVerityCode(String email,int code) throws MessageException {

        if (StringUtils.isEmpty(code)) {
            throw new MessageException(MessageCodeStorage.user_verity_code_empty);
        }

        UserVerityCodeBean userVerityCodeBean = verifiedCodeService.findVerityCodeByEmail(email);
        logger.info("UserInfoModel checkVerityCode userVerityCodeBean = " + JSON.toJSONString(userVerityCodeBean));
        // 没有信息，也给验证码无效的提示
        if (userVerityCodeBean == null) {
            throw new MessageException(MessageCodeStorage.user_verity_code_invalid);
        }

        // 验证码超过5分钟，给过期提示
        if (bases.getSystemSeconds() - userVerityCodeBean.getUpdate_time() > ConstantConfigure.FIVE_MINUTES_TIMES) {
            throw new MessageException(MessageCodeStorage.user_verity_code_out_time);
        }

        if (code != userVerityCodeBean.getVerity_code()) {
            throw new MessageException(MessageCodeStorage.user_verity_code_error);
        }
    }

    /**
     * 验证用户邮箱有效性
     *
     * @param email
     * @throws MessageException
     */
    public void checkUserEmail(String email) throws MessageException {

        // 验证邮箱是否为空
        if (StringUtils.isEmpty(email)) {
            throw new MessageException(MessageCodeStorage.user_email_empty);
        }

        // 判断邮箱格式是否正确
        boolean isEmail = bases.isEmail(email);
        if (!isEmail) {
            throw new MessageException(MessageCodeStorage.user_email_invalid);
        }

    }

    /**
     * 判断用户是否存在
     *
     * @param email
     * @throws MessageException
     */
    public UserInfoBean checkUserExists(String email) throws MessageException {
        // 验证用户信息是否存在
        UserInfoBean userInfoBean = userService.findUserByEmail(email);
        if (userInfoBean == null) {
            throw new MessageException(MessageCodeStorage.user_not_exists_error);
        }

        return userInfoBean;
    }

    /**
     * 对密码进行加密
     *
     * @param password
     * @return
     */
    public String encodePassword(String password) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(password);
        logger.info("encode hashPass = " + hashPass);
        return hashPass;
    }

    /**
     * 对密码进行比较
     *
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public boolean isPasswordRight(String rawPassword,String encodedPassword) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean f = bcryptPasswordEncoder.matches(rawPassword,encodedPassword);
        return f;
    }


}
