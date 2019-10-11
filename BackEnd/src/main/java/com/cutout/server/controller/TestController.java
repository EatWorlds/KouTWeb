package com.cutout.server.controller;

import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.service.AuthIgnore;
import com.cutout.server.service.ProductService;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author dimple
 *
 * 获取产品描述等相关信息
 */
@RestController
@RequestMapping("/v1")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取产品描述信息，主要是价格列表等
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @AuthIgnore
    public ResponseBean getTestInfo(@RequestParam String info) {
        String message = messageCodeStorage.success_code;

        try {

        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public ResponseBean getTestInfo1(@RequestParam String info) {
        String message = messageCodeStorage.success_code;

        try {

        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public ResponseBean getTestInfo2(@RequestParam String info) {
        String message = messageCodeStorage.success_code;

        try {

        } catch (Exception e) {

        }

        return responseHelperUtil.returnMessage(message);
    }


}
