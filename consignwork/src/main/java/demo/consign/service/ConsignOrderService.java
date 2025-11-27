package demo.consign.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import demo.consign.dto.ConsignOrderCreateRequest;
import demo.consign.entity.ConsignOrder;
import demo.consign.repository.ConsignOrderRepository;

@Service
public class ConsignOrderService {

    private final ConsignOrderRepository consignOrderRepository;

    public ConsignOrderService(ConsignOrderRepository consignOrderRepository) {
        this.consignOrderRepository = consignOrderRepository;
    }

    @Transactional
    public ConsignOrder createOrder(ConsignOrderCreateRequest req) {
        ConsignOrder order = new ConsignOrder();
        order.setShipperId(req.getShipperId());
        order.setStartAddress(req.getStartAddress());
        order.setEndAddress(req.getEndAddress());
        order.setGoodsDescription(req.getGoodsDescription());
        order.setTotalPrice(req.getTotalPrice());
        order.setStatus("PENDING");
        return consignOrderRepository.save(order);
    }

    public ConsignOrder getById(Long consignId) {
        return consignOrderRepository.findById(consignId)
                .orElseThrow(() -> new RuntimeException("委託單不存在"));
    }

    public List<ConsignOrder> findByShipper(Long shipperId) {
        return consignOrderRepository.findByShipperIdOrderByCreatedAtDesc(shipperId);
    }

    @Transactional
    public void markAsPaid(Long consignId) {
        ConsignOrder order = getById(consignId);
        order.setStatus("PAID");
        consignOrderRepository.save(order);
    }
}
