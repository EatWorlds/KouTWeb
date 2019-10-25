package com.cutout.server.model;

import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import com.cutout.server.service.ProductService;
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

    @Autowired
    private ProductService productService;

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

    /**
     * 验证产品有效性
     *
     * @param type 产品类型
     * @param productDetailBean 产品详情
     * @throws MessageException
     */
    public void checkProduct(Integer type,ProductDetailBean productDetailBean) throws MessageException {
        // 暂时只支持人脸，所以只有0
        if (type > 0) {
            throw new MessageException(MessageCodeStorage.user_order_type_not_exists);
        }

        ProductBean productBean = productService.getProductBean(type);
        if (productBean == null) {
            throw new MessageException(MessageCodeStorage.product_info_empty);
        }

        List<ProductDetailBean> productDetailBeans = productBean.getProductBeans();
        if (!productDetailBeans.contains(productDetailBean)) {
            throw new MessageException(MessageCodeStorage.user_order_detail_not_exists);
        }
    }

}
