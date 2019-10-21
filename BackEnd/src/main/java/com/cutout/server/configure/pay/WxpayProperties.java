package com.cutout.server.configure.pay;

import com.alipay.api.internal.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@Data
@ConfigurationProperties(prefix = "pay.wxpay1")
public class WxpayProperties {

    private Logger logger = LoggerFactory.getLogger(WxpayProperties.class);

    private String appId;
    private String appSecret;
    private String mchId;
    private String partnerKey;
    private String certPath;
    private String domain;

    @PostConstruct
    public void init() {
        logger.info(description());
        logger.info(toString());
    }
    @Override
    public String toString() {
        return "WxpayProperties [appId=" + appId + ", appSecret=" + appSecret + ", mchId=" + mchId + ", partnerKey="
                + partnerKey + ", certPath=" + certPath + ", domain=" + domain + "]";
    }

    public String description() {
        StringBuilder sb = new StringBuilder("\nConfigs{");
        sb.append("支付宝网关: ").append(appId).append("\n");

        sb.append("}");
        return sb.toString();
    }
}
