package com.cutout.server.service;

import com.cutout.server.domain.bean.OrderInfoBean;

/**
 * 订单公共类
 */
public interface PayCommonService {

    OrderInfoBean addOrder(OrderInfoBean orderInfoBean);

    OrderInfoBean findOrderByNo(String out_trade_no);

    OrderInfoBean updateOrderTimeByNo(String out_trade_no);
}
