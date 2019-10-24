package com.cutout.server.model;


import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.service.ProductService;
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
public class DownloadInfoModel {

    private Logger logger = LoggerFactory.getLogger(DownloadInfoModel.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 更新下载次数
     *
     * @param userInfoBean
     * @return
     */
    public UserInfoBean updateUserDownload(UserInfoBean userInfoBean) {
        Query query = Query.query(Criteria.where("email").is(userInfoBean.getEmail()));
        Update update = new Update();
        update.set("userDownload",userInfoBean.getUserDownload());
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,UserInfoBean.class);
        return mongoTemplate.findOne(query, UserInfoBean.class);
    }

}
