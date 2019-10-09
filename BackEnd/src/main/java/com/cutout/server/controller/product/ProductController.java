package com.cutout.server.controller.product;

import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.service.ProductService;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.plugin2.message.Message;

/**
 * @author dimple
 *
 * 获取产品描述等相关信息
 */
@RestController
@RequestMapping("/v1")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private ProductService productService;

    /**
     * 获取产品描述信息，主要是价格列表等
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/product/{type}", method = RequestMethod.GET)
    public ResponseBean getProductInfo(@PathVariable("type") int type) {
        String message = messageCodeStorage.success_code;
        ProductBean productBean = null;
        try {
            logger.info("getProductInfo = " + type);
            productBean = productService.getProductBean(type);
            if (productBean == null) {
                throw new MessageException(messageCodeStorage.product_info_empty);
            }


        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message,productBean);
    }

    /**
     * 添加产品信息
     * @return
     */
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseBean insertProductInfo() {
        String message = messageCodeStorage.success_code;
        ProductBean productBean = null;
        try {

           productBean =  productService.addProductBean(0);
            if (productBean == null) {
                throw new MessageException(messageCodeStorage.product_info_empty);
            }
        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message,productBean);
    }


}
