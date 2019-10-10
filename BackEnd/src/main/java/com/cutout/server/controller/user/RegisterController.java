package com.cutout.server.controller.user;

import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.UserInfoModel;
import com.cutout.server.service.UserService;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dimple
 *
 * 注册逻辑
 */
@RestController
@RequestMapping("/v1")
public class RegisterController {
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Bases bases;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private UserInfoModel userInfoModel;

    /**
     * 用户注册
     *
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseBean register(@RequestParam String email,@RequestParam String password) {

        String message = messageCodeStorage.success_code;
        Map<String,String> result = new HashMap<>();
        try {
            logger.info("register");

            // 用户有效性校验
            userInfoModel.checkUserInfo(email,password);

            // 验证用户信息是否存在，存在则不能注册
            UserInfoBean userInfoBean = userService.findUserByEmail(email);
            logger.info("result ==" + userInfoBean);
            if (userInfoBean != null) {
                throw new MessageException(messageCodeStorage.user_login_exists_error);
            }

            userInfoBean = new UserInfoBean();
            userInfoBean.setEmail(email);
            userInfoBean.setPassword(password);

            userInfoBean = userService.addUser(userInfoBean);
            // 没存储成功，返回注册失败
            if (userInfoBean == null) {
                userService.removeUser(email);
                throw new MessageException(messageCodeStorage.user_register_failed);
            }

            result.put(ConstantConfigure.RESULT_EMAIL,email);

        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {
            // 注册失败，删除mongodb里的数据，重新注册
            userService.removeUser(email);
            message = messageCodeStorage.user_register_failed;
        }

        return responseHelperUtil.returnMessage(message,result);
    }

    /**
     * 验证注册链接的code
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/user/{code}", method = RequestMethod.GET)
    public ResponseBean checkCode(@PathVariable("code") String code) {
        String message = messageCodeStorage.success_code;
        Map<String,String> result = new HashMap<>();
        try {
            logger.info("code = " + code);
            UserInfoBean userInfoBean = userService.findUserByCode(code);
            if (userInfoBean == null) {
                throw new MessageException(messageCodeStorage.user_login_check_code_error);
            }

            userService.updateUserByCode(code);

            result.put(ConstantConfigure.RESULT_EMAIL,userInfoBean.getEmail());
        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }

        return  responseHelperUtil.returnMessage(message,result);
    }
}
