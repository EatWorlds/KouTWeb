package com.cutout.server.configure.pay;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName WxpayConfiguration
 * @Description: 微信支付配置初始化
 * @Author Dimple
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
@Configuration
@EnableConfigurationProperties(WxpayProperties.class)
public class WxpayConfiguration {

    @Autowired
    private WxpayProperties wxpayProperties;


}
