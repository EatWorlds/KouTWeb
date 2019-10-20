package com.cutout.server.configure.pay;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KtWXPayConfig.class)
public class WXPayConfiguration {

    @Autowired
    private KtWXPayConfig wxPayConfig;

    /**
     * useSandbox 沙盒环境
     * @return
     */
    @Bean
    public WXPay wxPay() {
        return new WXPay(wxPayConfig, WXPayConstants.SignType.MD5, wxPayConfig.getUseSandbox() );
    }

    @Bean
    public WXPayClient wxPayClient() {
        return new WXPayClient(wxPayConfig, WXPayConstants.SignType.MD5, wxPayConfig.getUseSandbox());
    }

}
