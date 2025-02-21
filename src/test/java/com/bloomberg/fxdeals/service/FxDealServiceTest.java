package com.bloomberg.fxdeals.service;

import com.bloomberg.fxdeals.dto.fxDeals.FxDealDTO;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealResponseDto;
import com.bloomberg.fxdeals.exception.DuplicateDealException;
import com.bloomberg.fxdeals.exception.InvalidDealDataException;
import com.bloomberg.fxdeals.mapper.FxDealMapper;
import com.bloomberg.fxdeals.model.FxDeal;
import com.bloomberg.fxdeals.repository.FxDealRepository;
import com.bloomberg.fxdeals.service.impl.FxDealServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
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
    void processDeal_Success() {
        when(validator.validate(validDealRequest)).thenReturn(new HashSet<>());
        when(fxDealRepository.existsByDealUniqueId(validDealRequest.dealUniqueId())).thenReturn(false);
        when(fxDealMapper.toEntity(validDealRequest)).thenReturn(fxDeal);
        when(fxDealRepository.save(any(FxDeal.class))).thenReturn(fxDeal);

        FxDealResponseDto response = fxDealService.processDeal(validDealRequest);

        assertNotNull(response);
        assertEquals("DEAL123", response.dealUniqueId());
        assertEquals("SUCCESS", response.status());
        verify(fxDealRepository, times(1)).save(any(FxDeal.class));
    }

    @Test
    void processDeal_DuplicateDeal() {
        when(validator.validate(validDealRequest)).thenReturn(new HashSet<>());
        when(fxDealRepository.existsByDealUniqueId(validDealRequest.dealUniqueId())).thenReturn(true);

        assertThrows(DuplicateDealException.class, () -> fxDealService.processDeal(validDealRequest));
        verify(fxDealRepository, never()).save(any(FxDeal.class));
    }

    @Test
    void getDealById_Success() {
        String dealId = "DEAL123";
        when(fxDealRepository.findByDealUniqueId(dealId)).thenReturn(Optional.of(fxDeal));
        when(fxDealMapper.toDto(fxDeal)).thenReturn(fxDealDTO);

        FxDealDTO result = fxDealService.getDealById(dealId);

        assertNotNull(result);
        assertEquals(dealId, result.dealUniqueId());
        assertEquals("USD", result.fromCurrency());
        assertEquals("EUR", result.toCurrency());
    }

    @Test
    void getDealById_NotFound() {
        String dealId = "NONEXISTENT";
        when(fxDealRepository.findByDealUniqueId(dealId)).thenReturn(Optional.empty());

        assertThrows(InvalidDealDataException.class, () -> fxDealService.getDealById(dealId));
    }

    @Test
    void getAllDeals_Success() {
        when(fxDealRepository.findAll()).thenReturn(Collections.singletonList(fxDeal));
        when(fxDealMapper.toDto(fxDeal)).thenReturn(fxDealDTO);

        var results = fxDealService.getAllDeals();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("DEAL123", results.get(0).dealUniqueId());
    }
}