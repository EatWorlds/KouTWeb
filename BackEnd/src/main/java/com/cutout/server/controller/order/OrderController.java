package com.cutout.server.controller.order;

import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Description:
 * @Author xuyue
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
@RestController
@RequestMapping("/v1")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

}
