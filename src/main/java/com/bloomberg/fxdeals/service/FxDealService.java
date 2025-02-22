package com.bloomberg.fxdeals.service;


import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealResponseDto;


public interface FxDealService {
    FxDealResponseDto processDeal(FxDealRequestDto dealRequest);

}