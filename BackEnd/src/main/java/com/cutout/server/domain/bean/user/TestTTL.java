package com.cutout.server.domain.bean.user;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="testTTL")
@Data
public class TestTTL {
    @Id
    private String _id;

    //过期时间为1000秒
    @Indexed(expireAfterSeconds=100)
    private String ttl;

    private String testid;

    @Indexed(expireAfterSeconds=0)
    private Date expireTime;
}
