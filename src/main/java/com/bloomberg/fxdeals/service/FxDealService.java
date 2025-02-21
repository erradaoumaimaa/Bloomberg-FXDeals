package com.bloomberg.fxdeals.service;

import com.bloomberg.fxdeals.dto.fxDeals.FxDealDTO;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealResponseDto;

import java.util.List;

public interface FxDealService {
    FxDealResponseDto processDeal(FxDealRequestDto dealRequest);
    List<FxDealDTO> getAllDeals();
    FxDealDTO getDealById(String dealUniqueId);
}