package com.cutout.server.service.impl;

import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.OrderInfoBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import com.cutout.server.domain.bean.user.UserDownloadBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.DownloadInfoModel;
import com.cutout.server.model.OrderInfoModel;
import com.cutout.server.service.DownloadService;
import com.cutout.server.service.PayCommonService;
import com.cutout.server.utils.Bases;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    private Bases bases;

    @Autowired
    private DownloadInfoModel downloadInfoModel;

    /**
     * 初始化下载次数
     *
     * 激活初始化、本月第一次用初始化
     * @param userInfoBean
     * @return
     */
    @Override
    public UserInfoBean initDownload(UserInfoBean userInfoBean) {
        UserDownloadBean userDownloadBean = new UserDownloadBean();
        userDownloadBean.setUpdate_time(bases.getSystemSeconds());
        userDownloadBean.setValid_count(ConstantConfigure.INIT_DOWNLOAD_VALID_COUNT);
        userDownloadBean.setValid_time(bases.getMonthLastTime());
        userInfoBean.setUserDownload(userDownloadBean);
        return downloadInfoModel.updateUserDownload(userInfoBean);
    }

    /**
     * 购买产品之后更新下载次数
     *
     * @param userInfoBean
     * @param productDetailBean
     * @return
     */
    @Override
    public UserInfoBean updateDownloadByPay(UserInfoBean userInfoBean, ProductDetailBean productDetailBean) {
        UserDownloadBean userDownloadBean = userInfoBean.getUserDownload();
        if (userDownloadBean == null) {
            userDownloadBean = new UserDownloadBean();
            userDownloadBean.setValid_time(bases.getMonthLastTime());
        }

        userDownloadBean.setValid_count(userDownloadBean.getValid_count() + productDetailBean.getCount());
        userDownloadBean.setUpdate_time(bases.getSystemSeconds());
        userInfoBean.setUserDownload(userDownloadBean);

        return downloadInfoModel.updateUserDownload(userInfoBean);
    }

    /**
     * 下载之后，次数减少
     *
     * @param userInfoBean
     * @return
     */
    @Override
    public UserInfoBean updateDownloadByDown(UserInfoBean userInfoBean) {
        UserDownloadBean userDownloadBean = userInfoBean.getUserDownload();
        userDownloadBean.setUpdate_time(bases.getSystemSeconds());
        userDownloadBean.setValid_count(userDownloadBean.getValid_count() - 1);

        return downloadInfoModel.updateUserDownload(userInfoBean);
    }
}
