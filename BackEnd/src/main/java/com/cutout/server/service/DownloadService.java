package com.cutout.server.service;

import com.cutout.server.domain.bean.product.ProductDetailBean;
import com.cutout.server.domain.bean.user.UserInfoBean;

public interface DownloadService
{

    /**
     * 初始化下载次数
     *
     * 激活初始化、本月第一次用初始化
     * @param userInfoBean
     * @return
     */
    UserInfoBean initDownload(UserInfoBean userInfoBean);

    /**
     * 购买产品之后更新下载次数
     *
     * @param userInfoBean
     * @param productDetailBean
     * @return
     */
    UserInfoBean updateDownloadByPay(UserInfoBean userInfoBean, ProductDetailBean productDetailBean);

    /**
     * 下载之后，次数减少
     *
     * @param userInfoBean
     * @return
     */
    UserInfoBean updateDownloadByDown(UserInfoBean userInfoBean);
}
