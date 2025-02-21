package com.bloomberg.fxdeals.service.impl;

import com.bloomberg.fxdeals.dto.fxDeals.FxDealDTO;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealResponseDto;
import com.bloomberg.fxdeals.exception.DuplicateDealException;
import com.bloomberg.fxdeals.exception.InvalidDealDataException;
import com.bloomberg.fxdeals.mapper.FxDealMapper;
import com.bloomberg.fxdeals.model.FxDeal;
import com.bloomberg.fxdeals.repository.FxDealRepository;
import com.bloomberg.fxdeals.service.FxDealService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository fxDealRepository;
    private final FxDealMapper fxDealMapper;
    private final Validator validator;

    @Override
    @Transactional
    public FxDealResponseDto processDeal(FxDealRequestDto dealRequest) {
        log.info("Processing FX deal with ID: {}", dealRequest.dealUniqueId());

        Set<ConstraintViolation<FxDealRequestDto>> violations = validator.validate(dealRequest);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            log.error("Validation error for deal {}: {}", dealRequest.dealUniqueId(), errorMessage);
            throw new InvalidDealDataException(errorMessage);
        }

        if (fxDealRepository.existsByDealUniqueId(dealRequest.dealUniqueId())) {
            log.warn("Duplicate deal detected with ID: {}", dealRequest.dealUniqueId());
            throw new DuplicateDealException("Deal with ID " + dealRequest.dealUniqueId() + " already exists");
        }

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

    @Override
    @Transactional(readOnly = true)
    public List<FxDealDTO> getAllDeals() {
        log.info("Retrieving all FX deals");
        return fxDealRepository.findAll()
                .stream()
                .map(fxDealMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FxDealDTO getDealById(String dealUniqueId) {
        log.info("Retrieving FX deal with ID: {}", dealUniqueId);
        return fxDealRepository.findByDealUniqueId(dealUniqueId)
                .map(fxDealMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Deal not found with ID: {}", dealUniqueId);
                    return new InvalidDealDataException("Deal not found with ID: " + dealUniqueId);
                });
    }
}