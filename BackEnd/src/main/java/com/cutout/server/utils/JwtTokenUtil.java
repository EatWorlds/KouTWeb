package com.cutout.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cutout.server.constant.ConstantConfigure;
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
 * 参数说明
 * http://www.leftso.com/blog/221.html
 *
 * https://www.cnblogs.com/shihaiming/p/9565835.html
 *
 * 项目中使用的：
 * [基于JWT的token身份认证方案](https://www.cnblogs.com/xiangkejin/archive/2018/05/08/9011119.html)
 * @author Dimple 2019-09-21
 */
@Component
public class JwtTokenUtil {

    private Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    /**
     * 生成token,15分钟过期
     *
     * @param userInfoBean
     * @return
     */
    public String generateToken(UserInfoBean userInfoBean) {
        String token = "";
        try {
            // 签名算法，用密码加密
            Algorithm algorithm = Algorithm.HMAC256(userInfoBean.getPassword());
            Date date = new Date(System.currentTimeMillis() + ConstantConfigure.JWT_EXPIRE_TIME);

            JWTCreator.Builder builder = JWT.create()
                    // 返回该jwt由谁接收
                    .withAudience(userInfoBean.getEmail())
                    // 自定义
                    .withClaim(ConstantConfigure.RESULT_EMAIL,userInfoBean.getEmail())
                    .withClaim(ConstantConfigure.RESUTL_ID,userInfoBean.getId())
                    // 过期时间
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

    /**
     * token是否过期
     * @return true：过期
     */
    public boolean isTokenExpired(Date expiresAt) {
        return expiresAt.before(new Date());
    }
}
