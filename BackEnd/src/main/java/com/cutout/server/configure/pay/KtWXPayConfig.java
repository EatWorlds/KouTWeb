package com.cutout.server.configure.pay;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Data
@ConfigurationProperties(prefix = "pay.wxpay")
public class KtWXPayConfig implements WXPayConfig {

    private Logger logger = LoggerFactory.getLogger(KtWXPayConfig.class);

    /**
     * 公众账号ID
     */
    private String appID;

    /** 商户号 */
    private String mchID;

    /** API 密钥 */
    private String key;

    /** API 沙箱环境密钥 */
    private String sandboxKey;

    /** API证书绝对路径 */
    private String certPath;

    /** 退款异步通知地址 */
    private String notifyUrl;

    private Boolean useSandbox;

    private String unifiedOrderURL;

    /** HTTP(S) 连接超时时间，单位毫秒 */
    private int httpConnectTimeoutMs = 8000;

    /** HTTP(S) 读数据超时时间，单位毫秒 */
    private int httpReadTimeoutMs = 10000;

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream() {
        File certFile = new File(certPath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(certFile);
        } catch (FileNotFoundException e) {
            logger.error("cert file",e);
        }
        return inputStream;
    }

    @Override
    public String getKey() {
        if (useSandbox) {
            return sandboxKey;
        }
        return key;
    }


}
