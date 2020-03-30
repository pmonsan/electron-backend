package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.SubscriptionPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubscriptionPrice} and its DTO {@link SubscriptionPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {PricePlanMapper.class})
public interface SubscriptionPriceMapper extends EntityMapper<SubscriptionPriceDTO, SubscriptionPrice> {

    @Mapping(source = "pricePlan.id", target = "pricePlanId")
    SubscriptionPriceDTO toDto(SubscriptionPrice subscriptionPrice);

    @Mapping(source = "pricePlanId", target = "pricePlan")
    SubscriptionPrice toEntity(SubscriptionPriceDTO subscriptionPriceDTO);

    default SubscriptionPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubscriptionPrice subscriptionPrice = new SubscriptionPrice();
        subscriptionPrice.setId(id);
        return subscriptionPrice;
    }
}
