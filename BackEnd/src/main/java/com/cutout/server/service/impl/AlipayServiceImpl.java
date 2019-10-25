package com.cutout.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cutout.server.configure.pay.AlipayProperties;
import com.cutout.server.domain.bean.OrderInfoBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import com.cutout.server.model.OrderInfoModel;
import com.cutout.server.service.AlipayService;
import com.cutout.server.utils.UUIDUtil;
import com.ijpay.alipay.AliPayApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class AlipayServiceImpl implements AlipayService {

    private Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Autowired
    private UUIDUtil uuidUtil;

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private OrderInfoModel orderInfoModel;

    /**
     * 创建支付宝电脑网站支付的订单
     * @param email
     * @param type
     * @param productDetailBean
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String createPagePayOrder(String email, int type, ProductDetailBean productDetailBean) throws AlipayApiException {


        String productCode = "FAST_INSTANT_TRADE_PAY";
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        // 设置商户号
        String outTradeNo = uuidUtil.getUUIDCode();
        String amount = productDetailBean.getPrice() * productDetailBean.getDiscount() + "";

        OrderInfoBean orderInfoBean = orderInfoModel.createOrderInfo(outTradeNo, email, amount, type, 0, productDetailBean);

        model.setOutTradeNo(outTradeNo);
        model.setSubject(productDetailBean.getTitle());

        // 支付金额
        model.setTotalAmount(amount);
        model.setBody(JSON.toJSONString(orderInfoBean));
        model.setProductCode(productCode);

        AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();
        // 回调地址
        pagePayRequest.setReturnUrl(alipayProperties.getReturnUrl());
        // 支付宝异步通知地址
        pagePayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        pagePayRequest.setBizModel(model);

        // 这个过程会产生订单生成失败的情况
        String form = alipayClient.pageExecute(pagePayRequest).getBody();

        // 记录订单到mongodb数据库
        orderInfoBean = orderInfoModel.addOrderInfo(orderInfoBean);

        return form;
    }

    /**
     * 校验签名
     *
     * @param request
     * @return
     */
    @Override
    public boolean rsaCheckV1(HttpServletRequest request) {
        try {
            // https://docs.open.alipay.com/54/106370
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            // boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verifyResult = AlipaySignature.rsaCheckV1(params, alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(), alipayProperties.getSignType());
            return verifyResult;
        } catch (AlipayApiException e) {
            logger.error("AlipayController rsaCheckV1 AlipayApiException", e);
            return false;
        }
    }

    /**
     * 校验通知数据的正确性
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkNotify(HttpServletRequest request) {
        String charsetName1 = "ISO-8859-1";
        String charsetName2 = "UTF-8";
        /**
         * TODO 需要严格按照如下描述校验通知数据的正确性
         *
         * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
         * 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
         * 同时需要校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
         *
         * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
         * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
         * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
         */
        StringBuilder stringBuilder = new StringBuilder();
        // 交易状态
//        String tradeStatus = new String(request.getParameter("trade_status").getBytes(charsetName1),charsetName2);
        String tradeStatus = new String(request.getParameter("trade_status"));
        stringBuilder.append("tradeStatus").append(tradeStatus);
        // 商户订单号
//        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(charsetName1),charsetName2);
        String out_trade_no = new String(request.getParameter("out_trade_no"));
        stringBuilder.append("out_trade_no").append(out_trade_no);
        // 支付宝交易号
//        String trade_no = new String(request.getParameter("trade_no").getBytes(charsetName1),charsetName2);
        String trade_no = new String(request.getParameter("trade_no"));
        stringBuilder.append("trade_no").append(trade_no);
        // 付款金额
//        String total_amount = new String(request.getParameter("total_amount").getBytes(charsetName1),charsetName2);
        String total_amount = new String(request.getParameter("total_amount"));
        stringBuilder.append("total_amount").append(total_amount);

        String seller_id = new String(request.getParameter("seller_id"));
        stringBuilder.append("seller_id").append(seller_id);

        String body = new String(request.getParameter("body"));
        stringBuilder.append("body").append(body);

        logger.info("stringBuilder = " + stringBuilder.toString());
        // TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
        // TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
        if (tradeStatus.equals("TRADE_FINISHED")) {
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
            // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            //如果有做过处理，不执行商户的业务程序

            //注意：
            //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            return true;
        } else if (tradeStatus.equals("TRADE_SUCCESS")) {
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
            // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            //如果有做过处理，不执行商户的业务程序

            //注意：
            //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            return true;

        }
        return false;
    }

    /**
     * 创建PC支付订单
     *
     * @param response
     * @param email
     * @param type
     * @param productDetailBean
     * @throws AlipayApiException
     * @throws IOException
     */
    @Override
    public void createPcPayOrder(HttpServletResponse response, String email, int type, ProductDetailBean productDetailBean) throws AlipayApiException, IOException {

        String productCode = "FAST_INSTANT_TRADE_PAY";
        // 设置商户号
        String outTradeNo = uuidUtil.getUUIDCode();
        String amount = productDetailBean.getPrice() * productDetailBean.getDiscount() + "";

        // 创建订单之前，先查询是否有同类订单，待完成

        // 创建订单
        OrderInfoBean orderInfoBean = orderInfoModel.createOrderInfo(outTradeNo, email, amount, type, 0, productDetailBean);

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(outTradeNo);
        model.setProductCode(productCode);
        model.setTotalAmount(amount);// 支付金额
        model.setSubject(productDetailBean.getTitle());
//        model.setBody(JSON.toJSONString(orderInfoBean));

        AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();
        pagePayRequest.setReturnUrl(alipayProperties.getReturnUrl());// 回调地址
        pagePayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());// 支付宝异步通知地址
        pagePayRequest.setBizModel(model);

        // 这个过程会产生订单生成失败的情况
//        String form = alipayClient.pageExecute(pagePayRequest).getBody();
//
//        response.setContentType("text/html;charset=" + alipayProperties.getCharset());
//        response.getWriter().write(form);
//        response.getWriter().flush();
//        response.getWriter().close();
        // 会有 AlipayApiException, IOException 的错误
        AliPayApi.tradePage(response,model,alipayProperties.getNotifyUrl(),alipayProperties.getReturnUrl());

        // 记录订单到mongodb数据库
//        orderInfoBean = orderInfoModel.addOrderInfo(orderInfoBean);
    }
}
