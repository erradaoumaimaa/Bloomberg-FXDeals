package com.bloomberg.fxdeals.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record FxDealRequestDto(
        @NotBlank(message = "Deal Unique ID is required")
        String dealUniqueId,

        @NotBlank(message = "From Currency is required")
        @Pattern(regexp = "^[A-Z]{3}$", message = "From Currency must be a valid 3-letter ISO currency code")
        String fromCurrency,

        @NotBlank(message = "To Currency is required")
        @Pattern(regexp = "^[A-Z]{3}$", message = "To Currency must be a valid 3-letter ISO currency code")
        String toCurrency,

        @NotNull(message = "Deal timestamp is required")
        LocalDateTime dealTimestamp,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        Double amount
) {}