package com.cutout.server.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cutout.server.configure.pay.AlipayProperties;
import com.cutout.server.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author dimple
 *
 * 2019-10-15
 *
 * 支付宝网页支付
 *
 */
@RestController
@RequestMapping("/v1/alipay/page")
public class AlipayPagePayController {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayController alipayController;

    @Autowired
    private UUIDUtil uuidUtil;

    @PostMapping("/pagePay")
    public void alipayPage(HttpServletResponse response) throws IOException {
        String productCode = "FAST_INSTANT_TRADE_PAY";
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(uuidUtil.getUUIDCode());// 待规则制定
        model.setSubject("AlipayTest");
        model.setTotalAmount("0.1");// 支付金额
        model.setBody("AlipayTest 0.1");
        model.setProductCode(productCode);

        AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();
        pagePayRequest.setReturnUrl("");// 回调地址
        pagePayRequest.setNotifyUrl("");// 支付宝异步通知地址
        pagePayRequest.setBizModel(model);

        String form = "";
        try {
            form = alipayClient.pageExecute(pagePayRequest).getBody();
        } catch (AlipayApiException e) {

        }

        response.setContentType("text/html;charset=" + alipayProperties.getCharset());
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();

    }

    @RequestMapping("/returnUrl")
    public String returnUrl(HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException,AlipayApiException {
        response.setContentType("text/html;charset="+alipayProperties.getCharset());
        boolean verifyResult = alipayController.rsaCheckV1(request);
        if (verifyResult) {
            // 验证成功
            // 请在这里机上商户的业务逻辑程序代码，如保存支付宝交易号
            // 商户订单
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            return "pagePaySuccess";

        }
        return "pagePayFail";
    }
}
