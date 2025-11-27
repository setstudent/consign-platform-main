package demo.consign.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.consign.dto.ConsignOrderCreateRequest;
import demo.consign.entity.ConsignOrder;
import demo.consign.entity.Payment;
import demo.consign.service.ConsignOrderService;
import demo.consign.service.EcpayService;
import demo.consign.service.PaymentService;

@RestController
public class DemoController {

    private final ConsignOrderService consignOrderService;
    private final PaymentService paymentService;
    private final EcpayService ecpayService;

    public DemoController(ConsignOrderService consignOrderService,
                          PaymentService paymentService,
                          EcpayService ecpayService) {
        this.consignOrderService = consignOrderService;
        this.paymentService = paymentService;
        this.ecpayService = ecpayService;
    }

    @GetMapping("/demo/test-checkout")
    public ResponseEntity<String> demoCheckout() {
        ConsignOrderCreateRequest req = new ConsignOrderCreateRequest();
        req.setShipperId(1L);
        req.setStartAddress("台北市大安區測試路 1 號");
        req.setEndAddress("台中市西屯區示範街 99 號");
        req.setGoodsDescription("測試託運貨物");
        req.setTotalPrice(BigDecimal.valueOf(100));

        ConsignOrder order = consignOrderService.createOrder(req);
        Payment payment = paymentService.createPaymentForOrder(order, 1L);

        String html = ecpayService.generateCheckoutForm(payment, order);
        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }
}
