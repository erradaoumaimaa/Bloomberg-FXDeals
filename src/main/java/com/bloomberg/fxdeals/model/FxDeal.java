package com.bloomberg.fxdeals.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "fx_deals", uniqueConstraints = {
        @UniqueConstraint(name = "uk_deal_id", columnNames = {"dealUniqueId"})
})
@Data
public class FxDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String dealUniqueId;

    @Column(nullable = false, length = 3)
    private String fromCurrency;

    @Column(nullable = false, length = 3)
    private String toCurrency;

    @Column(nullable = false)
    private LocalDateTime dealTimestamp;

    @Column(nullable = false, precision = 20, scale = 4)
    private Double amount;
}
