package demo.consign.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String role; // SHIPPER / CARRIER / ADMIN

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String companyName;

    @Column(length = 500)
    private String defaultAddress;
}
