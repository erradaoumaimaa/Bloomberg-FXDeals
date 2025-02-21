package com.bloomberg.fxdeals.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fx_deals", uniqueConstraints = {
        @UniqueConstraint(name = "uk_deal_id", columnNames = {"dealUniqueId"})
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    @PastOrPresent(message = "Deal timestamp must be in the past or present.")
    private LocalDateTime dealTimestamp;

    @Column(nullable = false, precision = 20, scale = 4)
    private BigDecimal amount;
}