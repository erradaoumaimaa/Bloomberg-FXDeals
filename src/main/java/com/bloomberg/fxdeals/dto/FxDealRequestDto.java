package com.bloomberg.fxdeals.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
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
        @PastOrPresent(message = "Deal timestamp must be in the past or present.")
        LocalDateTime dealTimestamp,

        @Digits(integer = 8, fraction = 2, message = "Amount must be a valid number with up to 8 digits and 2 decimal places.")
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount
) {}