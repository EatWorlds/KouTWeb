package com.cutout.server.model;

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


@Component
public class UserMongoModel {

    private Logger logger = LoggerFactory.getLogger(UserMongoModel.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Bases bases;

    @Autowired
    private UUIDUtil uuidUtil;

    /**
     * 注册添加用户
     * @param userInfoBean
     * @return
     */
    public UserInfoBean addUser(UserInfoBean userInfoBean) {
        // 设置创建时间
        userInfoBean.setCreate_time(bases.getSystemSeconds());
        // 设置状态为0，注册未激活
        userInfoBean.setStatus(0);
        // 设置code
        userInfoBean.setCode(uuidUtil.getUUIDCode());
        userInfoBean.setCode_time(bases.getSystemSeconds());
        return mongoTemplate.save(userInfoBean);
    }

    /**
     * 根据邮箱获取用户信息
     * @param email
     * @return
     */
    public UserInfoBean findUserByEmail(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query,UserInfoBean.class);
    }

    /**
     * 根据验证码code获取用户信息
     * @param code
     * @return
     */
    public UserInfoBean findUserByCode(String code) {
        Query query = Query.query(Criteria.where("code").is(code));
        return mongoTemplate.findOne(query,UserInfoBean.class);
    }

    /**
     * code验证完成，修改状态，以及code置空
     * @param code
     * @return
     */
    public UpdateResult updateUserByCode(String code) {
        Query query = Query.query(Criteria.where("code").is(code));
        Update update = new Update();
        update.set("status",1);
//        update.set("code","");
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,UserInfoBean.class);

        return updateResult;
    }

    /**
     * 注册失败的时候，删除用户
     * @param email
     * @return
     */
    public DeleteResult removeUser(String email) {
//        Query query = Query.query(Criteria.where("email").is(userInfoBean.getEmail()));
//        Update update = new Update();
//        update.set("password",123456);
//        UpdateResult result = mongoTemplate.updateFirst(query,update,UserInfoBean.class);

        Query query = Query.query(Criteria.where("email").is(email));
        DeleteResult deleteResult = mongoTemplate.remove(query, UserInfoBean.class);
        System.out.println("removeUser = " + deleteResult);
        return deleteResult;
    }

    /**
     * 用户退出，清空token
     *
     * @param token
     * @return
     */
    public UpdateResult cleanUserToken(String token) {
        Query query = Query.query(Criteria.where("token").is(token));
        Update update = new Update();
        update.set("token","");
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,UserInfoBean.class);
        logger.info("============" + updateResult);
        return updateResult;
    }

    public UpdateResult updateUserWithLogin(String email,String token) {
        Query query = Query.query(Criteria.where("email").is(email));
        Update update = new Update();
        update.set("token",token);
        update.set("last_login",bases.getSystemSeconds());
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,UserInfoBean.class);

        return updateResult;
    }

    public UpdateResult updateUserCodeByEmail(UserInfoBean userInfoBean) {
        Query query = Query.query(Criteria.where("email").is(userInfoBean.getEmail()));
        Update update = new Update();
        update.set("code",userInfoBean.getCode());
        update.set("code_time",userInfoBean.getCode_time());
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,UserInfoBean.class);

        return updateResult;
    }


}
