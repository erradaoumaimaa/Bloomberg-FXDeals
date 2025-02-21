package com.bloomberg.fxdeals.mapper;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealRequestDto;
import com.bloomberg.fxdeals.dto.fxDeals.FxDealDTO;
import com.bloomberg.fxdeals.model.FxDeal;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FxDealMapper {
    FxDeal toEntity(FxDealRequestDto dealDto);
    FxDealDTO toDto(FxDeal deal);
}