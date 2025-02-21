package com.bloomberg.fxdeals.dto;

public record FxDealResponseDto(
        String dealUniqueId,
        String status,
        String message
) {}