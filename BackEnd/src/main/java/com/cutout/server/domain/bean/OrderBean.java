package com.cutout.server.domain.bean;

import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import lombok.Data;

@Data
public class OrderBean {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 创建时间
     */
    private int create_time;

    /**
     * 修改时间
     */
    private int update_time;

    /**
     * 支付渠道
     */
    private int pay_path;

    /**
     * 实际支付金额
     */
    private int amount;

    /**
     * 订单产品
     */
    private ProductBean product;
}
