package com.cutout.server.model;

import com.cutout.server.domain.bean.OrderInfoBean;
import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import com.cutout.server.utils.Bases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 订单相关操作
 */
@Component
public class OrderInfoModel {

    private Logger logger = LoggerFactory.getLogger(OrderInfoModel.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Bases bases;

    public OrderInfoBean getOrderInfo(String out_trade_no) {
        Query query = Query.query(Criteria.where("out_trade_no").is(out_trade_no));
        return mongoTemplate.findOne(query, OrderInfoBean.class);
    }

    public OrderInfoBean addOrderInfo(OrderInfoBean orderInfoBean) {

        return mongoTemplate.save(orderInfoBean);
    }

    /**
     * 创建记录到数据库的订单类，微信支付宝公用
     * @param outTradeNo
     * @param email
     * @param amount
     * @param type
     * @param payPath
     * @param productDetailBean
     * @return
     */
    public OrderInfoBean createOrderInfo(String outTradeNo,String email,String amount,int type,int payPath,ProductDetailBean productDetailBean) {
        OrderInfoBean orderInfoBean = new OrderInfoBean();
        // 设置商户订单号
        orderInfoBean.setOut_trade_no(outTradeNo);
        // 设置邮箱即用户名
        orderInfoBean.setEmail(email);
        // 设置支付渠道 0：支付宝，1：微信
        orderInfoBean.setPay_path(payPath);
        // 设置实际支付金额
        orderInfoBean.setAmount(amount);

        // 设置需要使用的产品功能，0：人脸
        orderInfoBean.setType(type);
        // 设置订单的详情
        orderInfoBean.setProductDetailBean(productDetailBean);
        // 设置创建时间
        orderInfoBean.setCreate_time(bases.getSystemSeconds());
        return orderInfoBean;
    }

}
