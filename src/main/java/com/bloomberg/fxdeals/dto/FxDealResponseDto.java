package com.bloomberg.fxdeals.dto;

import java.time.LocalDateTime;

public record FxDealResponseDto(
        Long id,
        String dealUniqueId,
        String fromCurrency,
        String toCurrency,
        LocalDateTime dealTimestamp,
        Double amount
) {}