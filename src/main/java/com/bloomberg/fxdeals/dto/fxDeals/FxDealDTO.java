package com.bloomberg.fxdeals.dto.fxDeals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FxDealDTO(
        String dealUniqueId,
        String fromCurrency,
        String toCurrency,
        LocalDateTime dealTimestamp,
        BigDecimal amount
) {}