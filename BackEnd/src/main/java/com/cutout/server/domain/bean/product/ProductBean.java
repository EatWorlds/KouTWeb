package com.cutout.server.domain.bean.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductBean {

    /**
     * 需要使用的产品功能，0：人脸
     */
    private int type;

    /**
     * 产品的具体描述
     */
    private List<ProductDetailBean> productBeans;
}
