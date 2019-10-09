package com.cutout.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cutout.server.domain.bean.user.UserInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken 生成的工具类
 *
 * @author Dimple 2019-09-21
 */
@Component
public class JwtTokenUtil {

    private Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    // 过期时间15分钟
    private static final long EXPIRE_TIME = 15 * 60 * 1000;

    /**
     * 生成token,15分钟过期
     *
     * @param userInfoBean
     * @return
     */
    public String generateToken(UserInfoBean userInfoBean) {
        String token = "";
        try {
            // 签名算法
            Algorithm algorithm = Algorithm.HMAC256(userInfoBean.getPassword());
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

            JWTCreator.Builder builder = JWT.create()
                    .withClaim("email",userInfoBean.getEmail())
                    .withExpiresAt(date);
            token = builder.sign(algorithm);

        } catch (Exception e) {
            logger.error("generateToken",e);
        }

        return token;
    }

    public Map<String,Object> verify(String token, UserInfoBean userInfoBean) {
        Algorithm algorithm = null;
        Map<String,Object> resultMap = null;
        try {
            algorithm = Algorithm.HMAC256(userInfoBean.getPassword());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> map = jwt.getClaims();
            logger.info("jwt.getExpiresAt()" + jwt.getExpiresAt());
            resultMap = new HashMap<>();
            for (Map.Entry<String,Claim> entry:
                    map.entrySet()) {
                resultMap.put(entry.getKey(),entry.getValue().asString());
            }

            resultMap.put("time",jwt.getExpiresAt());
        } catch (Exception e) {

        }
        return resultMap;
    }
}
