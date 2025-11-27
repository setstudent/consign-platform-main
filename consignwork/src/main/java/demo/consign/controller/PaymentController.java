package demo.consign.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import demo.consign.entity.ConsignOrder;
import demo.consign.entity.Payment;
import demo.consign.service.ConsignOrderService;
import demo.consign.service.EcpayService;
import demo.consign.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final ConsignOrderService consignOrderService;
    private final PaymentService paymentService;
    private final EcpayService ecpayService;

    public PaymentController(ConsignOrderService consignOrderService,
                             PaymentService paymentService,
                             EcpayService ecpayService) {
        this.consignOrderService = consignOrderService;
        this.paymentService = paymentService;
        this.ecpayService = ecpayService;
    }

    @PostMapping("/ecpay/checkout/{consignId}")
    public ResponseEntity<String> ecpayCheckout(@PathVariable Long consignId,
                                                @RequestParam Long payerId) {
        ConsignOrder order = consignOrderService.getById(consignId);
        Payment payment = paymentService.createPaymentForOrder(order, payerId);
        String html = ecpayService.generateCheckoutForm(payment, order);
        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }
}
