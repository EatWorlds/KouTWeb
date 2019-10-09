package com.cutout.server.service.impl;

import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.ProductInfoModel;
import com.cutout.server.model.UserMongoModel;
import com.cutout.server.service.MallService;
import com.cutout.server.service.ProductService;
import com.cutout.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoModel productInfoModel;

    @Override
    public ProductBean addProductBean(int type) {
        return productInfoModel.addProductInfo(type);
    }

    @Override
    public ProductBean getProductBean(int type) {
        return productInfoModel.getProductInfo(type);
    }
}
