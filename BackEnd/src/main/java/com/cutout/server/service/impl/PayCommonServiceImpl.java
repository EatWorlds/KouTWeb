package com.cutout.server.service.impl;

import com.cutout.server.domain.bean.OrderInfoBean;
import com.cutout.server.model.OrderInfoModel;
import com.cutout.server.service.AuthIgnore;
import com.cutout.server.service.PayCommonService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayCommonServiceImpl implements PayCommonService {

    @Autowired
    private OrderInfoModel orderInfoModel;

    @Override
    public OrderInfoBean addOrder(OrderInfoBean orderInfoBean) {
        return null;
    }

    @Override
    public OrderInfoBean findOrderByNo(String out_trade_no) {
        return orderInfoModel.findOrderByNo(out_trade_no);
    }

    @Override
    public OrderInfoBean updateOrderTimeByNo(String out_trade_no) {
        UpdateResult updateResult = orderInfoModel.updateOrderTimeByNo(out_trade_no);
        return findOrderByNo(out_trade_no);
    }
}
