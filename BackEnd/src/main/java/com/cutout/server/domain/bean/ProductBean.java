package com.cutout.server.domain.bean;

import lombok.Data;

@Data
public class ProductBean {

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
