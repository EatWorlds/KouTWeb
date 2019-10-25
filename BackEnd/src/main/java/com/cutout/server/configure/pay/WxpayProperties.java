package com.cutout.server.configure.pay;

import com.alipay.api.internal.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @ClassName WxpayProperties
 * @Description: 获取配置文件中 pay.wxpay1的内容
 * @Author Dimple
 * @Date 2019/10/25 0025
 * @Version V1.0
**/
@Data
@Slf4j
@ConfigurationProperties(prefix = "pay.wxpay1")
public class WxpayProperties {

    private String appId;
    private String appSecret;
    private String mchId;
    private String partnerKey;
    private String certPath;
    private String domain;

    @PostConstruct
    public void init() {
        log.info(description());
    }

    public String description() {
        String description = "WxpayProperties [appId=" + appId + ", appSecret=" + appSecret + ", mchId=" + mchId + ", partnerKey="
                + partnerKey + ", certPath=" + certPath + ", domain=" + domain + "]";
        return description;
    }
}
