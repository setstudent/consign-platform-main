package demo.consign.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "ecpay")
@Data
public class EcpayConfig {
    private String environment;
    private String merchantId;
    private String hashKey;
    private String hashIv;
    private String returnUrl;
    private String orderResultUrl;

    public String getApiUrl() {
        if ("production".equalsIgnoreCase(environment)) {
            return "https://payment.ecpay.com.tw/Cashier/AioCheckOut/V5";
        }
        return "https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5";
    }
}
