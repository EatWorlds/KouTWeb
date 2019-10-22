package com.cutout.server.model;

import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.domain.bean.user.UserVerityCodeBean;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.UUIDUtil;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


@Component
public class UserVerityCodeModel {

    private Logger logger = LoggerFactory.getLogger(UserVerityCodeModel.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Bases bases;

    /**
     * 创建用户验证码内容
     *
     * @param email
     * @return
     */
    public UserVerityCodeBean addVerityCode(String email) {
        UserVerityCodeBean userVerityCodeBean = new UserVerityCodeBean();
        userVerityCodeBean.setEmail(email);
        userVerityCodeBean.setVerity_code(bases.getRandom(000000,999999));
        userVerityCodeBean.setUpdate_time(bases.getSystemSeconds());
        return mongoTemplate.save(userVerityCodeBean);
    }

    /**
     * 通过邮箱查找验证码内容
     *
     * @param email
     * @return
     */
    public UserVerityCodeBean findVerityCodeByEmail(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query,UserVerityCodeBean.class);
    }

    public UserVerityCodeBean updateVerityCodeByEmail(UserVerityCodeBean userVerityCodeBean) {
        Query query = Query.query(Criteria.where("email").is(userVerityCodeBean.getEmail()));
        int verityCode = bases.getRandom(000000,999999);
        int time = bases.getSystemSeconds();
        userVerityCodeBean.setVerity_code(verityCode);
        userVerityCodeBean.setUpdate_time(time);
        Update update = new Update();
        update.set("verity_code",userVerityCodeBean.getVerity_code());
        update.set("update_time",userVerityCodeBean.getUpdate_time());
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,UserVerityCodeBean.class);
        logger.info("UserVerityCodeModel updateVerityCodeByEmail updateResult = " + updateResult);
        return userVerityCodeBean;
    }

}
