package demo.consign.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long consignId;

    @Column(nullable = false)
    private Long payerId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 30)
    private String method; // ECPAY

    @Column(nullable = false, length = 20)
    private String status; // PENDING / PAID / FAILED

    private String merchantTradeNo;

    private String tradeNo;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "PENDING";
    }
}
