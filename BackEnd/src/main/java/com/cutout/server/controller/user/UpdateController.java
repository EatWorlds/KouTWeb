package com.cutout.server.controller.user;

import com.alibaba.fastjson.JSON;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 忘记密码，给邮箱发送一封邮件，获取验证码
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseBean forgetPwd(@RequestParam String email) {
        String message = messageCodeStorage.success_code;
        try {
            logger.info("forgetPwd email = " + email);

        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }
}
