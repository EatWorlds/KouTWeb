package com.cutout.server.service;

import com.alipay.api.AlipayApiException;
import com.cutout.server.domain.bean.product.ProductDetailBean;

import javax.servlet.http.HttpServletRequest;

public interface AlipayService {

    /**
     * 创建电脑网站支付的订单
     * @return
     */
    String createPagePayOrder(ProductDetailBean productDetailBea) throws AlipayApiException;

    boolean rsaCheckV1(HttpServletRequest request);

    boolean checkNotify(HttpServletRequest request);
}
