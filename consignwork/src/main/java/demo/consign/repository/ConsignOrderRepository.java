package demo.consign.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import demo.consign.entity.ConsignOrder;

public interface ConsignOrderRepository extends JpaRepository<ConsignOrder, Long> {

    List<ConsignOrder> findByShipperIdOrderByCreatedAtDesc(Long shipperId);
}
