package com.bloomberg.fxdeals.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fx_deals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FxDeal {

    @Id
    @Column(nullable = false, unique = true, length = 50)
    private String dealUniqueId;

    @Column(nullable = false, length = 3)
    private String fromCurrency;

    @Column(nullable = false, length = 3)
    private String toCurrency;

    @Column(nullable = false)
    private LocalDateTime dealTimestamp;

    @Column(nullable = false, precision = 20, scale = 4)
    private Double dealAmount;
}
