package com.cutout.server.controller.user;

import com.alibaba.fastjson.JSON;
import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.domain.bean.user.UserVerityCodeBean;
import com.cutout.server.service.AuthIgnore;
import com.cutout.server.service.MailService;
import com.cutout.server.service.UserService;
import com.cutout.server.service.VerityCodeService;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.ResponseHelperUtil;
import com.cutout.server.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dimple
 *
 * 更新用户相关信息：修改用户信息，忘记密码
 */
@RestController
@RequestMapping("/v1")
public class UpdateController {

    private Logger logger = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private VerityCodeService verityCodeService;

    @Autowired
    private Bases bases;

    @Autowired
    private UserService userService;

    /**
     * 更新用户信息
     *
     * @param id
     * @param userInfoBean
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PATCH)
    public ResponseBean update(@PathVariable("id") String id, UserInfoBean userInfoBean) {
        String message = messageCodeStorage.success_code;
        try {
            logger.info("update id = " + id);
            logger.info("update email = " + JSON.toJSON(userInfoBean));
        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }

    /**
     * 注册/忘记密码，给邮箱发送一封邮件，获取验证码
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    @AuthIgnore
    public ResponseBean getVerityCode(@RequestParam String email) {
        String message = messageCodeStorage.success_code;
        UserVerityCodeBean userVerityCodeBean = null;
        try {
            logger.info("getVerityCode email = " + email);
            userVerityCodeBean = verityCodeService.findVerityCodeByEmail(email);

            // 如果没有该用户，则直接新建一条数据到数据库
            if (userVerityCodeBean == null) {
                userVerityCodeBean = verityCodeService.addVerityCode(email);
            } else { // 如果有该用户，进行相应的判断以及修改
                int lastTime = userVerityCodeBean.getUpdate_time();
                // 请求时间在60之内的，提示操作过于频繁
                if (bases.getSystemSeconds() - lastTime < ConstantConfigure.ONE_MINUTE_TIMES) {
                    throw new MessageException(messageCodeStorage.request_busy);
                }

                userVerityCodeBean = verityCodeService.updateVerityCodeByEmail(userVerityCodeBean);
            }

            logger.info("UpdateController userVerityCodeBean = " + JSON.toJSONString(userVerityCodeBean));
            mailService.sendVerityCode(userVerityCodeBean);
        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {
            logger.error("",e);
        }

        return responseHelperUtil.returnMessage(message,userVerityCodeBean);
    }

    /**
     * 重新发送邮箱验证码
     * @param email
     * @return
     */
    @RequestMapping(value = "/email/{email}", method = RequestMethod.PATCH)
    @AuthIgnore
    public ResponseBean resendCheckEmail(@PathVariable("email") String email) {
        String message = messageCodeStorage.success_code;
        Map<String,String> result = new HashMap<>();
        try {
            logger.info("resendCheckEmail = " + email);
            UserInfoBean userInfoBean = userService.findUserByEmail(email);
            if (userInfoBean == null) {
                throw new MessageException(messageCodeStorage.user_not_exists_error);
            }

            int timeDiff = bases.getSystemSeconds() - userInfoBean.getCode_time();
            logger.info("resendCheckEmail timeDiff = " + timeDiff);

            // 1分钟之内不允许重复点击链接
            if (timeDiff < ConstantConfigure.ONE_MINUTE_TIMES) {
                throw new MessageException(messageCodeStorage.request_busy);
            }

            // 更新用户邮箱验证码
            userInfoBean = userService.updateUserCodeByEmail(userInfoBean);
            logger.info("UpdateController userInfoBean = " + JSON.toJSONString(userInfoBean));
            mailService.sendHtmlMail(userInfoBean);

            result.put(ConstantConfigure.RESULT_EMAIL,userInfoBean.getEmail());
        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }

        return  responseHelperUtil.returnMessage(message,result);
    }
}
