package com.cutout.server.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.configure.pay.AlipayProperties;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.service.AlipayService;
import com.cutout.server.utils.ResponseHelperUtil;
import com.cutout.server.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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

    private Logger logger = LoggerFactory.getLogger(AlipayPagePayController.class);

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayController alipayController;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    /**
     *
     * @param response
     * @param email  下订单的用户
     * @param type    订单类型 0：人脸
     * @param productDetailBean 订单详情
     * @return
     * @throws IOException
     */
    @PostMapping("/pagePay")
    public ResponseBean alipayPage(HttpServletResponse response, String email, int type,ProductDetailBean productDetailBean) {
        String message = messageCodeStorage.success_code;
        Map<String,String> map = new HashMap<>();
        try {
            String form = alipayService.createPagePayOrder(email,type,productDetailBean);
            map.put(ConstantConfigure.RESULT_EMAIL,email);
            response.setContentType("text/html;charset=" + alipayProperties.getCharset());
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException alipayException) {
            message = messageCodeStorage.user_create_order_failed;
        } catch (IOException ioException){
            message = messageCodeStorage.user_create_order_failed;
        } catch (Exception e) {
            message = messageCodeStorage.user_create_order_failed;
        }
        return responseHelperUtil.returnMessage(message,map);
    }

    @RequestMapping("/returnUrl")
    public String returnUrl(HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException,AlipayApiException {
        response.setContentType("text/html;charset="+alipayProperties.getCharset());
        boolean verifyResult = alipayService.rsaCheckV1(request);
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
