package demo.consign.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "consign_orders")
@Data
public class ConsignOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consignId;

    @Column(nullable = false)
    private Long shipperId;

    private Long carrierId;

    @Column(nullable = false, length = 500)
    private String startAddress;

    @Column(nullable = false, length = 500)
    private String endAddress;

    @Column(nullable = false, length = 500)
    private String goodsDescription;

    private BigDecimal goodsWeight;

    private BigDecimal goodsVolume;

    private LocalDateTime expectedPickupTime;

    @Column(nullable = false, length = 20)
    private String status; // PENDING / PAID / DELIVERING / DELIVERED / CANCELLED

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "PENDING";
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
