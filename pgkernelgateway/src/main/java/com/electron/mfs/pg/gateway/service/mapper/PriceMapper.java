package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Price} and its DTO {@link PriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {PricePlanMapper.class})
public interface PriceMapper extends EntityMapper<PriceDTO, Price> {

    @Mapping(source = "pricePlan.id", target = "pricePlanId")
    PriceDTO toDto(Price price);

    @Mapping(source = "pricePlanId", target = "pricePlan")
    Price toEntity(PriceDTO priceDTO);

    default Price fromId(Long id) {
        if (id == null) {
            return null;
        }
        Price price = new Price();
        price.setId(id);
        return price;
    }
}
