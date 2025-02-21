package com.bloomberg.fxdeals.dto.fxDeals;

public record FxDealResponseDto(
        String dealUniqueId,
        String status,
        String message
) {}