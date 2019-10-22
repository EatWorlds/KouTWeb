package com.cutout.server.service;

import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.user.UserInfoBean;

public interface ProductService
{

    /**
     * 创建产品描述
     *
     * @param type
     * @return
     */
    ProductBean addProductBean(int type);

    /**
     * 获取产品描述
     *
     * @param type
     * @return
     */
    ProductBean getProductBean(int type);
}
