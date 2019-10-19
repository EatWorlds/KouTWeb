package com.cutout.server.service;

import com.alipay.api.AlipayApiException;
import com.cutout.server.domain.bean.product.ProductDetailBean;

import javax.servlet.http.HttpServletRequest;

public interface AlipayService {

    /**
     * 创建支付宝电脑网站支付的订单
     * @param email
     * @param type
     * @param productDetailBean
     * @return
     * @throws AlipayApiException
     */
    String createPagePayOrder(String email,int type,ProductDetailBean productDetailBean) throws AlipayApiException;

    /**
     * 校验签名
     * @param request
     * @return
     */
    boolean rsaCheckV1(HttpServletRequest request);

    /**
     * 校验通知数据的正确性
     * @param request
     * @return
     */
    boolean checkNotify(HttpServletRequest request);
}
