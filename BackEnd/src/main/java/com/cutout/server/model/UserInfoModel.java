package com.cutout.server.model;

import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.domain.bean.user.UserInfoBean;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class UserInfoModel {

    private Logger logger = LoggerFactory.getLogger(UserInfoModel.class);

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private Bases bases;

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
            throw new MessageException(messageCodeStorage.user_email_password_empty);
        }

        // 判断邮箱格式是否正确
        boolean isEmail = bases.isEmail(email);
        if (!isEmail) {
            throw new MessageException(messageCodeStorage.user_email_invalid);
        }

    }


}
