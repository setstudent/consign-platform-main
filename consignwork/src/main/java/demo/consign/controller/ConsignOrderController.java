package demo.consign.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import demo.consign.dto.ConsignOrderCreateRequest;
import demo.consign.entity.ConsignOrder;
import demo.consign.service.ConsignOrderService;

@RestController
@RequestMapping("/api/consign")
public class ConsignOrderController {

    private final ConsignOrderService consignOrderService;

    public ConsignOrderController(ConsignOrderService consignOrderService) {
        this.consignOrderService = consignOrderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<ConsignOrder> create(@Validated @RequestBody ConsignOrderCreateRequest req) {
        return ResponseEntity.ok(consignOrderService.createOrder(req));
    }

    @GetMapping("/orders/{consignId}")
    public ResponseEntity<ConsignOrder> get(@PathVariable Long consignId) {
        return ResponseEntity.ok(consignOrderService.getById(consignId));
    }

    @GetMapping("/shipper/{shipperId}/orders")
    public ResponseEntity<List<ConsignOrder>> list(@PathVariable Long shipperId) {
        return ResponseEntity.ok(consignOrderService.findByShipper(shipperId));
    }
}
