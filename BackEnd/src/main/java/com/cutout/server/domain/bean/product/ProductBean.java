package com.cutout.server.domain.bean.product;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ProductBean
 * @Description: 产品大类
 * @Author Dimple
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
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
