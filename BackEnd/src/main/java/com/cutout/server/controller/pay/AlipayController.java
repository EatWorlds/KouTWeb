package com.cutout.server.controller.pay;


import com.alipay.api.AlipayApiException;
import com.cutout.server.service.AlipayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/v1/alipay")
public class AlipayController {

    private Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private AlipayService alipayService;

    /**
     * 支付异步通知
     *
     * 接收到异步通知并验签通过后，一定要检查通知内容，包括通知中的app_id,out_trade_no,total_amount是否与请求中的一致，并根据trade_status进行后续业务处理
     *
     * https://docs.open.alipay.com/194/103296
     */
    @RequestMapping("/notify")
    public String notify(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        // 一定要验签，防止黑客篡改参数
        Map<String,String[]> parameterMap = request.getParameterMap();
        StringBuilder stringBuilder = new StringBuilder("/****************************** alipay notify ******************************/\n");
        parameterMap.forEach((key,value) -> stringBuilder.append(key + "=" + value[0] + "\n"));
        logger.info(stringBuilder.toString());

        boolean flag = alipayService.rsaCheckV1(request);

        if (flag) {

            boolean notify = alipayService.checkNotify(request);
            return "success";
        }

        return "fail";
    };

}
