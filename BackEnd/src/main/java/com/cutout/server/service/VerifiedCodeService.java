package com.cutout.server.service;

import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.domain.bean.user.UserVerityCodeBean;

/**
 * 验证码相关
 */
public interface VerifiedCodeService
{

    /**
     * 创建验证码
     *
     * @param email
     * @return
     */
    UserVerityCodeBean addVerityCode(String email);

    /**
     * 通过邮箱查找验证码
     *
     * @param email
     * @return
     */
    UserVerityCodeBean findVerityCodeByEmail(String email);

    UserVerityCodeBean updateVerityCodeByEmail(UserVerityCodeBean userVerityCodeBean);
}
