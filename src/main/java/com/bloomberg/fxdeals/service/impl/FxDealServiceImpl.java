package com.bloomberg.fxdeals.service.impl;

import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealResponseDto;
import com.bloomberg.fxdeals.exception.DuplicateDealException;
import com.bloomberg.fxdeals.mapper.FxDealMapper;
import com.bloomberg.fxdeals.model.FxDeal;
import com.bloomberg.fxdeals.repository.FxDealRepository;
import com.bloomberg.fxdeals.service.FxDealService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository fxDealRepository;
    private final FxDealMapper fxDealMapper;

    @Override
    @Transactional
    public FxDealResponseDto processDeal(FxDealRequestDto dealRequest) {
        log.info("Processing FX deal with ID: {}", dealRequest.dealUniqueId());

        validateDeal(dealRequest);

        try {
            FxDeal fxDeal = fxDealMapper.toEntity(dealRequest);
            fxDealRepository.save(fxDeal);
            log.info("Successfully saved FX deal with ID: {}", dealRequest.dealUniqueId());
            return new FxDealResponseDto(dealRequest.dealUniqueId(), "SUCCESS", "Deal processed successfully");
        } catch (Exception e) {
            log.error("Error saving deal {}: {}", dealRequest.dealUniqueId(), e.getMessage(), e);
            return new FxDealResponseDto(dealRequest.dealUniqueId(), "ERROR", e.getMessage());
        }
    }

    private void validateDeal(FxDealRequestDto dealRequest) {
        if (fxDealRepository.findByDealUniqueId(dealRequest.dealUniqueId()).isPresent()) {
            log.warn("Duplicate deal detected with ID: {}", dealRequest.dealUniqueId());
            throw new DuplicateDealException("Deal with ID " + dealRequest.dealUniqueId() + " already exists");
        }
    }
}