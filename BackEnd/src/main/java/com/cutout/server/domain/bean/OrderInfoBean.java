package com.cutout.server.domain.bean;

import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import lombok.Data;

@Data
public class OrderInfoBean {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户email信息
     */
    private String email;

    /**
     * 创建时间
     */
    private int create_time;

    /**
     * 订单支付成功，创建修改时间，否则不更新
     */
    private int update_time;

    /**
     * 支付渠道
     */
    private int pay_path;

    /**
     * 实际支付金额
     */
    private String amount;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 需要使用的产品功能，0：人脸
     */
    private int type;

    /**
     * 订单产品
     */
    private ProductDetailBean productDetailBean;
}
