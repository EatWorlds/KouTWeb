package com.cutout.server.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.service.AuthIgnore;
import com.cutout.server.service.ProductService;
import com.cutout.server.utils.JwtTokenUtil;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author dimple
 *
 * 获取产品描述等相关信息
 */
@RestController
@RequestMapping("/v1")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 获取产品描述信息，主要是价格列表等
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @AuthIgnore
    public ResponseBean getTestInfo(@RequestParam String info) {
        String message = messageCodeStorage.success_code;

        try {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setEmail("98989@mail.com");
            userInfoBean.setPassword("123456");

            String token = jwtTokenUtil.generateToken(userInfoBean);
            logger.info("token ===== " + token);

//            String userEmail = JWT.decode(token).getAudience().get(0);
//            logger.info("userEmail = " + userEmail);
//
//
//            Map<String,Object> test = jwtTokenUtil.verify(token,userInfoBean);
//            Date time = (Date)test.get("time");
//            logger.info("time === " + time);
//            logger.info("is out = " + jwtTokenUtil.isTokenExpired(time));
//            logger.info(JSON.toJSONString(test));
        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public ResponseBean getTestInfo1(@RequestParam String info, HttpServletRequest request) {
        String message = messageCodeStorage.success_code;

        try {
            Object object = request.getAttribute(ConstantConfigure.USER_ATTRIBUTE_KEY);
            logger.info("object === " + JSON.toJSONString(object));
        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public void getTestInfo2(@RequestParam String info, HttpServletResponse response) {
        String message = messageCodeStorage.success_code;

        try {
            response.sendRedirect("https://www.baidu.com");
        } catch (Exception e) {

        }
return;

    }


}
