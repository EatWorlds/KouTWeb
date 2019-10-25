package com.cutout.server.controller.pay.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.configure.pay.AlipayProperties;
import com.cutout.server.configure.pay.WxpayProperties;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.OrderInfoBean;
import com.cutout.server.domain.bean.product.ProductBean;
import com.cutout.server.domain.bean.product.ProductDetailBean;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.domain.bean.user.UserInfoBean;
import com.cutout.server.model.ProductInfoModel;
import com.cutout.server.model.UserInfoModel;
import com.cutout.server.service.*;
import com.cutout.server.utils.ResponseHelperUtil;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.RsaKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>IJPay 让支付触手可及，封装了微信支付、支付宝支付、银联支付常用的支付方式以及各种常用的接口。</p>
 *
 * <p>不依赖任何第三方 mvc 框架，仅仅作为工具使用简单快速完成支付模块的开发，可轻松嵌入到任何系统里。 </p>
 *
 * <p>IJPay 交流群: 723992875</p>
 *
 * <p>Node.js 版: https://gitee.com/javen205/TNW</p>
 *
 * <p>支付宝支付 Demo</p>
 *
 * @author Javen
 */
@Controller
@RequestMapping("/v1/aliPay")
@EnableConfigurationProperties(WxpayProperties.class)
public class AliPayController extends AbstractAliPayApiController {
    private static final Logger logger = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private PayCommonService payCommonService;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private UserInfoModel userInfoModel;

    @Autowired
    private ProductInfoModel productInfoModel;

    @Override
    public AliPayApiConfig getApiConfig() {
        AliPayApiConfig aliPayApiConfig;
        try {
            aliPayApiConfig = AliPayApiConfigKit.getApiConfig(alipayProperties.getAppid());
        } catch (Exception e) {
            aliPayApiConfig = AliPayApiConfig.builder()
                    .setAppId(alipayProperties.getAppid())
                    .setAliPayPublicKey(alipayProperties.getAlipayPublicKey())
                    .setCharset(alipayProperties.getCharset())
                    .setPrivateKey(alipayProperties.getAppPrivateKey())
                    .setServiceUrl(alipayProperties.getServerUrl())
                    .setSignType(alipayProperties.getSignType())
                    .build();
        }

        return aliPayApiConfig;
    }

    @RequestMapping("")
    @ResponseBody
    public String index() {
        return "aliPay";
    }

    @RequestMapping("/test")
    @ResponseBody
    public AliPayApiConfig test() {
        AliPayApiConfig aliPayApiConfig = AliPayApiConfigKit.getAliPayApiConfig();
        String charset = aliPayApiConfig.getCharset();
        logger.info("charset>" + charset);
        return aliPayApiConfig;
    }

    /**
     *
     * @param response
     * @param email 给某个账号充值
     * @param type  充值类型，默认为0：人脸，可以不传
     * @param productDetailBean 订单详情
     */
    @RequestMapping("/pcPay")
    @ResponseBody
    @AuthIgnore
    public ResponseBean pcPay(HttpServletResponse response, String email, @RequestParam(defaultValue = "0") Integer type, ProductDetailBean productDetailBean) {
        logger.info("email = " + email + " type = " + type);
        logger.info("productDetailBean = " + productDetailBean);
        String message = MessageCodeStorage.success_code;
        Map<String,String> map = new HashMap<>();
        try {
            map.put(ConstantConfigure.RESULT_EMAIL,email);

            // 用户有效性校验
            userInfoModel.checkUserEmail(email);

           // 判断用户是否存在
            userInfoModel.checkUserExists(email);

            // 验证产品有效性
            productInfoModel.checkProduct(type,productDetailBean);

            // 下订单
            alipayService.createPcPayOrder(response,email,type,productDetailBean);
        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (Exception e) {
            logger.error("pcPay",e);
            message = MessageCodeStorage.user_create_order_failed;
        }

        return responseHelperUtil.returnMessage(message,map);
    }

    /**
     * 下载对账单
     */
    @RequestMapping(value = "/dataDataserviceBill")
    @ResponseBody
    public String dataDataserviceBill(@RequestParam("billDate") String billDate) {
        try {
            AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
            model.setBillType("trade");
            model.setBillDate(billDate);
            return AliPayApi.billDownloadurlQuery(model);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 交易查询
     */
    @RequestMapping(value = "/tradeQuery")
    @ResponseBody
    public boolean tradeQuery() {
        boolean isSuccess = false;
        try {
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo("081014283315023");
            model.setTradeNo("2017081021001004200200273870");

            isSuccess = AliPayApi.tradeQueryToResponse(model).isSuccess();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 创建订单
     * {"alipay_trade_create_response":{"code":"10000","msg":"Success","out_trade_no":"081014283315033","trade_no":"2017081021001004200200274066"},"sign":"ZagfFZntf0loojZzdrBNnHhenhyRrsXwHLBNt1Z/dBbx7cF1o7SZQrzNjRHHmVypHKuCmYifikZIqbNNrFJauSuhT4MQkBJE+YGPDtHqDf4Ajdsv3JEyAM3TR/Xm5gUOpzCY7w+RZzkHevsTd4cjKeGM54GBh0hQH/gSyhs4pEN3lRWopqcKkrkOGZPcmunkbrUAF7+AhKGUpK+AqDw4xmKFuVChDKaRdnhM6/yVsezJFXzlQeVgFjbfiWqULxBXq1gqicntyUxvRygKA+5zDTqE5Jj3XRDjVFIDBeOBAnM+u03fUP489wV5V5apyI449RWeybLg08Wo+jUmeOuXOA=="}
     */
    @RequestMapping(value = "/tradeCreate")
    @ResponseBody
    public String tradeCreate(@RequestParam("out_trade_no") String outTradeNo) {

//        String notifyUrl = aliPayBean.getDomain() + "/aliPay/notify_url";

        String notifyUrl = alipayProperties.getNotifyUrl();

        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount("88.88");
        model.setBody("Body");
        model.setSubject("Javen 测试统一收单交易创建接口");
        //买家支付宝账号，和buyer_id不能同时为空
        model.setBuyerLogonId("abpkvd0206@sandbox.com");
        try {
            AlipayTradeCreateResponse response = AliPayApi.tradeCreateToResponse(model, notifyUrl);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 撤销订单
     */
    @RequestMapping(value = "/tradeCancel")
    @ResponseBody
    public boolean tradeCancel() {
        boolean isSuccess = false;
        try {
            AlipayTradeCancelModel model = new AlipayTradeCancelModel();
            model.setOutTradeNo("081014283315033");
            model.setTradeNo("2017081021001004200200274066");

            isSuccess = AliPayApi.tradeCancelToResponse(model).isSuccess();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/tradeClose")
    @ResponseBody
    public String tradeClose(@RequestParam("out_trade_no") String outTradeNo, @RequestParam("trade_no") String tradeNo) {
        try {
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();
            model.setOutTradeNo(outTradeNo);

            model.setTradeNo(tradeNo);

            return AliPayApi.tradeCloseToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 2019-10-21 获取同步消息
     * @param request
     * @return
     */
    @RequestMapping(value = "/returnUrl")
    @ResponseBody
    public String returnUrl(HttpServletRequest request) {
        try {
            // 获取支付宝GET过来反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                logger.info(entry.getKey() + " = " + entry.getValue());
            }

//            boolean verifyResult = AlipaySignature.rsaCheckV1(map, aliPayBean.getPublicKey(), "UTF-8",
//                    "RSA2");
            boolean verifyResult = AlipaySignature.rsaCheckV1(map, alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(),
                    alipayProperties.getSignType());

            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码
                System.out.println("return_url 验证成功");

                return "success";
            } else {
                System.out.println("return_url 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }


    /**
     * 2019-10-21 支付宝异步通知的消息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/notifyUrl")
    @ResponseBody
    public String notifyUrl(HttpServletRequest request) {
        try {

            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);
            logger.info("notifyUrl = " + JSON.toJSONString(params));
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                log.info(entry.getKey() + " = " + entry.getValue());
//            }

            boolean verifyResult = AlipaySignature.rsaCheckV1(params, alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(), alipayProperties.getSignType());

            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
                boolean notify = alipayService.checkNotify(request);
                logger.info("notify_url 验证成功succcess");
                // 修改订单更新时间,多次请求,保证只更新一次update_time到数据库
                String out_trade_no = new String(request.getParameter("out_trade_no"));
                OrderInfoBean orderInfoBean = payCommonService.findOrderByNo(out_trade_no);
                if (orderInfoBean.getUpdate_time() <= 0) {
                    orderInfoBean = payCommonService.updateOrderTimeByNo(out_trade_no);
                }
                return "success";
            } else {
                logger.info("notify_url 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }
}