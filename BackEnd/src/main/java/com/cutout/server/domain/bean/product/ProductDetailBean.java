package com.cutout.server.domain.bean.product;

import lombok.Data;

/**
 * @ClassName ProductDetailBean
 * @Description: 产品明细
 * @Author Dimple
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
@Data
public class ProductDetailBean {

    /**
     * 产品描述
     */
    private String title;

    /**
     * 价格
     */
    private int price;

    /**
     * 可用次数
     */
    private int count;

    /**
     * 折扣比例
     */
    private float discount;
}
