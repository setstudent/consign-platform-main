package demo.consign.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import demo.consign.entity.ConsignOrder;
import demo.consign.entity.Payment;
import demo.consign.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment createPaymentForOrder(ConsignOrder order, Long payerId) {
        Payment payment = new Payment();
        payment.setConsignId(order.getConsignId());
        payment.setPayerId(payerId);
        BigDecimal amount = order.getTotalPrice();
        if (amount == null) {
            amount = BigDecimal.valueOf(100);
        }
        payment.setAmount(amount);
        payment.setMethod("ECPAY");
        payment.setStatus("PENDING");
        String merchantTradeNo = "CP" + (System.currentTimeMillis() % 1000000000L);
        merchantTradeNo = merchantTradeNo + UUID.randomUUID().toString().replaceAll("-", "").substring(0,
                Math.max(0, 20 - merchantTradeNo.length()));
        if (merchantTradeNo.length() > 20) {
            merchantTradeNo = merchantTradeNo.substring(0, 20);
        }
        payment.setMerchantTradeNo(merchantTradeNo);
        return paymentRepository.save(payment);
    }

    public Payment findByMerchantTradeNo(String merchantTradeNo) {
        return paymentRepository.findByMerchantTradeNo(merchantTradeNo)
                .orElseThrow(() -> new RuntimeException("找不到付款資料"));
    }

    @Transactional
    public void markPaid(Payment payment, String tradeNo) {
        payment.setStatus("PAID");
        payment.setTradeNo(tradeNo);
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }
}
