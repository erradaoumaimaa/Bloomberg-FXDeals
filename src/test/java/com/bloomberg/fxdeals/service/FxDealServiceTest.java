package com.bloomberg.fxdeals.service;

import com.bloomberg.fxdeals.dto.fxDeals.FxDealDTO;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealResponseDto;
import com.bloomberg.fxdeals.exception.DuplicateDealException;
import com.bloomberg.fxdeals.mapper.FxDealMapper;
import com.bloomberg.fxdeals.model.FxDeal;
import com.bloomberg.fxdeals.repository.FxDealRepository;
import com.bloomberg.fxdeals.service.impl.FxDealServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FxDealServiceTest {

    @Mock
    private FxDealRepository fxDealRepository;

    @Mock
    private FxDealMapper fxDealMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private FxDealServiceImpl fxDealService;

    private FxDealRequestDto validDealRequest;
    private FxDeal fxDeal;
    private FxDealDTO fxDealDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        validDealRequest = new FxDealRequestDto(
                "DEAL123",
                "USD",
                "EUR",
                now,
                BigDecimal.valueOf(1000)
        );

        fxDeal = FxDeal.builder()
                .id(1L)
                .dealUniqueId("DEAL123")
                .fromCurrency("USD")
                .toCurrency("EUR")
                .dealTimestamp(now)
                .amount(BigDecimal.valueOf(1000))
                .build();

        fxDealDTO = new FxDealDTO(
                "DEAL123",
                "USD",
                "EUR",
                now,
                BigDecimal.valueOf(1000)
        );
    }

    @Test
    void givenValidDealRequest_whenSaveFails_thenReturnErrorResponse() {
        when(fxDealRepository.findByDealUniqueId(validDealRequest.dealUniqueId())).thenReturn(Optional.empty());
        when(fxDealMapper.toEntity(validDealRequest)).thenReturn(fxDeal);
        when(fxDealRepository.save(any(FxDeal.class))).thenThrow(new RuntimeException("Database error"));

        FxDealResponseDto response = fxDealService.processDeal(validDealRequest);

        assertNotNull(response);
        assertEquals("DEAL123", response.dealUniqueId());
        assertEquals("ERROR", response.status());
        assertEquals("Database error", response.message());
    }

    @Test
    void givenValidDealRequest_whenProcessingDeal_thenEntityIsMappedCorrectly() {
        when(fxDealRepository.findByDealUniqueId(validDealRequest.dealUniqueId())).thenReturn(Optional.empty());
        when(fxDealMapper.toEntity(validDealRequest)).thenReturn(fxDeal);
        when(fxDealRepository.save(any(FxDeal.class))).thenReturn(fxDeal);

        fxDealService.processDeal(validDealRequest);

        ArgumentCaptor<FxDeal> dealCaptor = ArgumentCaptor.forClass(FxDeal.class);
        verify(fxDealRepository).save(dealCaptor.capture());

        FxDeal capturedDeal = dealCaptor.getValue();
        assertEquals(validDealRequest.dealUniqueId(), capturedDeal.getDealUniqueId());
        assertEquals(validDealRequest.fromCurrency(), capturedDeal.getFromCurrency());
        assertEquals(validDealRequest.toCurrency(), capturedDeal.getToCurrency());
        assertEquals(validDealRequest.amount(), capturedDeal.getAmount());
    }

}
