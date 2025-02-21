package com.bloomberg.fxdeals.mapper;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealResponseDto;
import com.bloomberg.fxdeals.model.FxDeal;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FxDealMapper {

    FxDealResponseDto toResponse(FxDeal fxDeal);

    FxDeal toEntity(FxDealRequestDto FxDealRequestDTO);

}