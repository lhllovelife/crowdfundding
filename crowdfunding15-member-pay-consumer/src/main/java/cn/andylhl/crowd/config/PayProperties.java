package cn.andylhl.crowd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @Title: PayProperties
 * @Description: 支付相关配置
 * @author: lhl
 * @date: 2021/1/25 16:11
 */

@Component // 加入到ioc容器中
@ConfigurationProperties(prefix = "ali.pay")
public class PayProperties {

    // 应用id
    private String appId;
    // 商户私钥
    private String merchantPrivateKey;
    // 支付宝公钥
    private String alipayPublicKey;
    // 服务器异步通知页面路径
    private String notifyUrl;
    // 页面跳转同步通知页面路径
    private String returnUrl;
    // 签名方式
    private String signType;
    // 字符编码格式
    private String charset;
    // 支付宝网关
    private String gatewayUrl;

    public PayProperties() {
    }

    public PayProperties(String appId, String merchantPrivateKey, String alipayPublicKey, String notifyUrl, String returnUrl, String signType, String charset, String gatewayUrl) {
        this.appId = appId;
        this.merchantPrivateKey = merchantPrivateKey;
        this.alipayPublicKey = alipayPublicKey;
        this.notifyUrl = notifyUrl;
        this.returnUrl = returnUrl;
        this.signType = signType;
        this.charset = charset;
        this.gatewayUrl = gatewayUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    @Override
    public String toString() {
        return "PayProperties{" +
                "appId='" + appId + '\'' +
                ", merchantPrivateKey='" + merchantPrivateKey + '\'' +
                ", alipayPublicKey='" + alipayPublicKey + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", signType='" + signType + '\'' +
                ", charset='" + charset + '\'' +
                ", gatewayUrl='" + gatewayUrl + '\'' +
                '}';
    }
}
