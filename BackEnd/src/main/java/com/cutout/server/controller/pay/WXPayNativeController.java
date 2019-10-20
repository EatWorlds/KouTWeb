package com.cutout.server.controller.pay;

import com.cutout.server.configure.pay.WXPayClient;
import com.cutout.server.utils.PayUtil;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/wxpay/nativePay")
public class WXPayNativeController {

    private Logger logger = LoggerFactory.getLogger(WXPayNativeController.class);
    @Autowired
    private WXPay wxPay;

    @Autowired
    private WXPayClient wxPayClient;

    public void nativePay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> reqData = new HashMap<>();
        reqData.put("out_trade_no","111");
        reqData.put("trade_type","NATIVE");
        reqData.put("product_id","1");
        reqData.put("body","商户下单");
        // 订单总金额，单位为分
        reqData.put("total_fee","2");
        // APP和网页支付提交用户端IP，Native支付填调用微信支付API的机器IP
        reqData.put("spbill_create_ip","");
        // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
        reqData.put("notify_url","");
        // 自定义参数，可以为终端编号（门店号或者收银设备ID），PC网页或公众号支付可以传WEB
        reqData.put("device_info","WEB");
        // 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
        reqData.put("attach","");

        /**
         * {
         * code_url=weixin://wxpay/bizpayurl?pr=vvz4xwC,
         * trade_type=NATIVE,
         * return_msg=OK,
         * result_code=SUCCESS,
         * return_code=SUCCESS,
         * prepay_id=wx18111952823301d9fa53ab8e1414642725
         * }
         */
        Map<String,String> responseMap = wxPay.unifiedOrder(reqData);
        logger.info("" + responseMap.toString());
        String returnCode = responseMap.get("return_code");
        String resultCode = responseMap.get("result_code");
        if (WXPayConstants.SUCCESS.equals(returnCode) && WXPayConstants.SUCCESS.equals(resultCode)) {
            String prepayId = responseMap.get("return_code");
            String codeUrl = responseMap.get("code_url");

            BufferedImage image = PayUtil.getQRCodeImage(codeUrl);
            response.setContentType("image/jpeg");
            response.setHeader("Pragma","no-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setIntHeader("Expires",-1);
            ImageIO.write(image,"JPEG",response.getOutputStream());
        }

    }

    /**
     * 异步回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/notify")
    public void precreateNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, String> reqData = wxPayClient.getNotifyParameter(request);

        /**
         * {
         * transaction_id=4200000138201806180751222945,
         * nonce_str=aaaf3fe4d3aa44d8b245bc6c97bda7a8,
         * bank_type=CFT,
         * openid=xxx,
         * sign=821A5F42F5E180ED9EF3743499FBCF13,
         * fee_type=CNY,
         * mch_id=xxx,
         * cash_fee=1,
         * out_trade_no=186873223426017,
         * appid=xxx,
         * total_fee=1,
         * trade_type=NATIVE,
         * result_code=SUCCESS,
         * time_end=20180618131247,
         * is_subscribe=N,
         * return_code=SUCCESS
         * }
         */
        logger.info("" + reqData.toString());

        // 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
        boolean signatureValid = wxPay.isPayResultNotifySignatureValid(reqData);
        if (signatureValid) {
            /**
             * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
             * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，
             * 判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
             * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
             */

            Map<String, String> responseMap = new HashMap<>(2);
            responseMap.put("return_code", "SUCCESS");
            responseMap.put("return_msg", "OK");
            String responseXml = WXPayUtil.mapToXml(responseMap);

            response.setContentType("text/xml");
            response.getWriter().write(responseXml);
            response.flushBuffer();
        }
    }

}
