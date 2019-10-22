package com.cutout.server.utils;


import com.cutout.server.domain.bean.user.UserInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {

    private Logger logger = LoggerFactory.getLogger(UUIDUtil.class);

    public String getUUIDCode() {
//        https://www.cnblogs.com/duzhentong/p/7816539.html
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        logger.info("UUIDUtil getUUIDCode = " + uuid);
        return uuid;
    }
}
