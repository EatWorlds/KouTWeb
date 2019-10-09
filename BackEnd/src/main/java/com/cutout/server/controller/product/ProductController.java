package com.cutout.server.controller.product;

import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 获取产品描述信息，主要是价格列表等
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/product/{type}", method = RequestMethod.GET)
    public ResponseBean getProductInfo(@PathVariable("type") String type) {
        String message = messageCodeStorage.success_code;
        try {
            logger.info("getProductInfo = " + type);

        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseBean insertProductInfo() {
        String message = messageCodeStorage.success_code;
        try {


        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }


}
