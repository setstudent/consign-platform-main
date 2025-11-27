package demo.consign.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import demo.consign.config.EcpayConfig;
import demo.consign.entity.Payment;
import demo.consign.service.ConsignOrderService;
import demo.consign.service.PaymentService;
import demo.consign.util.CheckMacValueUtil;

@RestController
@RequestMapping("/api/payment/ecpay")
public class EcpayCallbackController {

    private static final Logger log = LoggerFactory.getLogger(EcpayCallbackController.class);

    private final EcpayConfig ecpayConfig;
    private final PaymentService paymentService;
    private final ConsignOrderService consignOrderService;

    public EcpayCallbackController(EcpayConfig ecpayConfig,
                                   PaymentService paymentService,
                                   ConsignOrderService consignOrderService) {
        this.ecpayConfig = ecpayConfig;
        this.paymentService = paymentService;
        this.consignOrderService = consignOrderService;
    }

    @PostMapping("/return")
    public String handleReturn(@RequestParam Map<String, String> params) {
        log.info("ECPay ReturnURL params: {}", params);

        boolean valid = CheckMacValueUtil.verify(params, ecpayConfig.getHashKey(), ecpayConfig.getHashIv());
        if (!valid) {
            log.warn("CheckMacValue 驗證失敗");
            return "0|CheckMacValueError";
        }

        String rtnCode = params.get("RtnCode");
        String merchantTradeNo = params.get("MerchantTradeNo");
        String tradeAmt = params.get("TradeAmt");
        String tradeNo = params.get("TradeNo");

        Payment payment = paymentService.findByMerchantTradeNo(merchantTradeNo);

        if (payment.getAmount().setScale(0).toPlainString().equals(tradeAmt)) {
            if ("1".equals(rtnCode)) {
                paymentService.markPaid(payment, tradeNo);
                consignOrderService.markAsPaid(payment.getConsignId());
                log.info("付款成功，已更新訂單與付款狀態");
            } else {
                log.warn("付款失敗，RtnCode={}", rtnCode);
            }
        } else {
            log.warn("金額不一致，系統金額={}，ECPay金額={}", payment.getAmount(), tradeAmt);
            return "0|AmountMismatch";
        }

        return "1|OK";
    }
}
