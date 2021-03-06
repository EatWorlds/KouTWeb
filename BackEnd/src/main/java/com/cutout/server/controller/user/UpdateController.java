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
import com.cutout.server.service.VerifiedCodeService;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.ResponseHelperUtil;
import com.cutout.server.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UpdateController
 * @Description: 更新用户信息
 * @Author Dimple
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
@RestController
@RequestMapping("/v1")
public class UpdateController {

    private Logger logger = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private VerifiedCodeService verifiedCodeService;

    @Autowired
    private Bases bases;

    @Autowired
    private UserService userService;

    /**
     * 更新用户信息
     *
     * @param
     * @param userInfoBean
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PATCH)
    @AuthIgnore
    public ResponseBean update(@PathVariable("id") String id, @RequestParam(defaultValue = "") String oriEmail, UserInfoBean userInfoBean) {
        String message = MessageCodeStorage.success_code;
        try {
            logger.info("update id = " + id);
            logger.info("update oriEmail = " + oriEmail);
            logger.info("update userInfoBean = " + userInfoBean);

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
    @RequestMapping(value = "/VerifiedCode", method = RequestMethod.GET)
    @AuthIgnore
    public ResponseBean getVerifiedCode(@RequestParam String email) {
        String message = MessageCodeStorage.success_code;
        UserVerityCodeBean userVerityCodeBean = null;
        try {
            logger.info("getVerifiedCode email = " + email);
            userVerityCodeBean = verifiedCodeService.findVerityCodeByEmail(email);

            // 如果没有该用户，则直接新建一条数据到数据库
            if (userVerityCodeBean == null) {
                userVerityCodeBean = verifiedCodeService.addVerityCode(email);
            } else { // 如果有该用户，进行相应的判断以及修改
                int lastTime = userVerityCodeBean.getUpdate_time();
                // 请求时间在60之内的，提示操作过于频繁
                if (bases.getSystemSeconds() - lastTime < ConstantConfigure.ONE_MINUTE_TIMES) {
                    throw new MessageException(MessageCodeStorage.request_busy);
                }

                userVerityCodeBean = verifiedCodeService.updateVerityCodeByEmail(userVerityCodeBean);
            }

            logger.info("UpdateController userVerityCodeBean = " + JSON.toJSONString(userVerityCodeBean));
            mailService.sendVerifiedCode(userVerityCodeBean);
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
        String message = MessageCodeStorage.success_code;
        Map<String,String> result = new HashMap<>();
        try {
            logger.info("resendCheckEmail = " + email);
            if (StringUtils.isEmpty(email)) {
                throw new MessageException(MessageCodeStorage.user_email_empty);
            }
            UserInfoBean userInfoBean = userService.findUserByEmail(email);
            if (userInfoBean == null) {
                throw new MessageException(MessageCodeStorage.user_not_exists_error);
            }

            int timeDiff = bases.getSystemSeconds() - userInfoBean.getCode_time();
            logger.info("resendCheckEmail timeDiff = " + timeDiff);

            // 1分钟之内不允许重复点击链接
            if (timeDiff < ConstantConfigure.ONE_MINUTE_TIMES) {
                throw new MessageException(MessageCodeStorage.request_busy);
            }

            // 更新用户邮箱验证码
            userInfoBean = userService.updateUserCodeByEmail(userInfoBean);
            logger.info("UpdateController userInfoBean = " + JSON.toJSONString(userInfoBean));
            mailService.sendHtmlMail(userInfoBean);

            result.put(ConstantConfigure.RESULT_EMAIL,userInfoBean.getEmail());
        } catch (MessageException messageException) {
            logger.error("resendCheckEmail",messageException.getMessage());
            message = messageException.getMessage();
        } catch (Exception e) {
            logger.error("resendCheckEmail exception",e.getMessage());
            message = e.getMessage();
        }

        return  responseHelperUtil.returnMessage(message,result);
    }
}
