package com.cutout.server.controller.order;

import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dimple
 *
 * 支付下单相关
 */
@RestController
@RequestMapping("/v1")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;




}
