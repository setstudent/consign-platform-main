package demo.consign.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import demo.consign.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByMerchantTradeNo(String merchantTradeNo);
}
