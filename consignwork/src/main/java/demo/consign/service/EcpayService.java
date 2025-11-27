package demo.consign.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import demo.consign.config.EcpayConfig;
import demo.consign.entity.ConsignOrder;
import demo.consign.entity.Payment;
import demo.consign.util.CheckMacValueUtil;

@Service
public class EcpayService {

    private final EcpayConfig ecpayConfig;

    public EcpayService(EcpayConfig ecpayConfig) {
        this.ecpayConfig = ecpayConfig;
    }

    public String generateCheckoutForm(Payment payment, ConsignOrder order) {
        Map<String, String> params = new HashMap<>();
        params.put("MerchantID", ecpayConfig.getMerchantId());
        params.put("MerchantTradeNo", payment.getMerchantTradeNo());
        params.put("MerchantTradeDate", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        params.put("PaymentType", "aio");
        params.put("TotalAmount", payment.getAmount().setScale(0).toPlainString());
        params.put("TradeDesc", "託運委託平台付款");
        params.put("ItemName", order.getGoodsDescription() == null ? "託運服務x1" : order.getGoodsDescription());
        params.put("ReturnURL", ecpayConfig.getReturnUrl());
        params.put("OrderResultURL", ecpayConfig.getOrderResultUrl());
        params.put("ChoosePayment", "ALL");
        params.put("EncryptType", "1");

        String checkMacValue = CheckMacValueUtil.generate(params, ecpayConfig.getHashKey(), ecpayConfig.getHashIv());
        params.put("CheckMacValue", checkMacValue);

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<form id='ecpayForm' method='post' action='").append(ecpayConfig.getApiUrl()).append("'>");
        for (Map.Entry<String, String> e : params.entrySet()) {
            sb.append("<input type='hidden' name='").append(e.getKey()).append("' value='")
                    .append(e.getValue().replace("'", "&#39;")).append("'>");
        }
        sb.append("</form>");
        sb.append("<script>document.getElementById('ecpayForm').submit();</script>");
        sb.append("</body></html>");
        return sb.toString();
    }
}
