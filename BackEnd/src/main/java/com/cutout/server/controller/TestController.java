package com.cutout.server.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.UserInfoModel;
import com.cutout.server.service.AuthIgnore;
import com.cutout.server.service.DownloadService;
import com.cutout.server.service.ProductService;
import com.cutout.server.service.UserService;
import com.cutout.server.utils.JwtTokenUtil;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@Controller
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserInfoModel userInfoModel;

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private UserService userService;

    /**
     * 获取产品描述信息，主要是价格列表等
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @AuthIgnore
    public ResponseBean getTestInfo(@RequestParam String info) {
        String message = MessageCodeStorage.success_code;

        try {
            UserInfoBean userInfoBean = userService.findUserByEmail(info);
            logger.info(JSON.toJSONString(userInfoBean));
            logger.info(userInfoBean.getId());

            userInfoBean = userService.findUserById(userInfoBean.getId());
            logger.info("after === " + JSON.toJSONString(userInfoBean));
//            ProductDetailBean productDetailBean = new ProductDetailBean();
//            productDetailBean.setCount(10);
//            downloadService.initDownload(userInfoBean);
//            downloadService.updateDownloadByPay(userInfoBean,productDetailBean);
//            downloadService.updateDownloadByDown(userInfoBean);
        } catch (Exception e) {
            logger.error("getTestInfo",e);
        }

        return responseHelperUtil.returnMessage(message);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    @AuthIgnore
    public ResponseBean getTestInfo1(@RequestParam String info, HttpServletRequest request) {
        String message = MessageCodeStorage.success_code;

        try {
//            Object object = request.getAttribute(ConstantConfigure.USER_ATTRIBUTE_KEY);
//            logger.info("object === " + JSON.toJSONString(object));

            String pass = info;
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashPass = bcryptPasswordEncoder.encode(pass);
            System.out.println(hashPass);

            boolean f = new BCryptPasswordEncoder().matches(info,"$2a$10$t4BpXV4KNPV/SQn6D2w7FOaOdwnhOhI5JK4YiTy4/vFzs4sAMb4Cy");
            System.out.println(f);

            String result = userInfoModel.encodePassword(info);
            logger.info("result ============== " + result);

            boolean is = userInfoModel.isPasswordRight(info,"$2a$10$t4BpXV4KNPV/SQn6D2w7FOaOdwnhOhI5JK4YiTy4/vFzs4sAMb4Ce");
            logger.info("is = " + is);

        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public void getTestInfo2(@RequestParam String info, HttpServletResponse response) {
        String message = MessageCodeStorage.success_code;

        try {
            response.sendRedirect("https://www.baidu.com");
        } catch (Exception e) {

        }
return;

    }

    @RequestMapping("/index")
    @AuthIgnore
    public String indexJumpPage() {
        return "index";
    }


}
