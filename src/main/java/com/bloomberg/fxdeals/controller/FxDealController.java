package com.bloomberg.fxdeals.controller;

import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealResponseDto;
import com.bloomberg.fxdeals.service.FxDealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/fx-deals")
@RequiredArgsConstructor
@Slf4j
public class FxDealController {

    private final FxDealService fxDealService;

    @PostMapping
    public ResponseEntity<FxDealResponseDto> processDeal(@Valid @RequestBody FxDealRequestDto dealRequest) {
        log.info("Received request to process FX deal: {}", dealRequest.dealUniqueId());
        FxDealResponseDto response = fxDealService.processDeal(dealRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}