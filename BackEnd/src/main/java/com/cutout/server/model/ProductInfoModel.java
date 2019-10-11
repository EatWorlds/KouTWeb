package com.cutout.server.model;

import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProductInfoModel {

    private Logger logger = LoggerFactory.getLogger(ProductInfoModel.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public ProductBean getProductInfo(int type) {
        Query query = Query.query(Criteria.where("type").is(type));
        return mongoTemplate.findOne(query, ProductBean.class);
    }

    public ProductBean addProductInfo(int type) {
        ProductBean productBean = new ProductBean();
        productBean.setType(type);
        List<ProductDetailBean> productDetailBeans = new ArrayList<>();
        ProductDetailBean productDetailBean = new ProductDetailBean();
        productDetailBean.setTitle("50次");
        productDetailBean.setCount(50);
        productDetailBean.setDiscount(1);
        productDetailBean.setPrice(69);
        productDetailBeans.add(productDetailBean);

        productDetailBean = new ProductDetailBean();
        productDetailBean.setTitle("200次");
        productDetailBean.setCount(200);
        productDetailBean.setDiscount(1);
        productDetailBean.setPrice(249);
        productDetailBeans.add(productDetailBean);

        productBean.setProductBeans(productDetailBeans);

        return mongoTemplate.save(productBean);
    }

}
