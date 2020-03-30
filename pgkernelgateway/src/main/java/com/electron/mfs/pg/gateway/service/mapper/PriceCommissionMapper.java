package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PriceCommissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PriceCommission} and its DTO {@link PriceCommissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriceCommissionMapper extends EntityMapper<PriceCommissionDTO, PriceCommission> {



    default PriceCommission fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceCommission priceCommission = new PriceCommission();
        priceCommission.setId(id);
        return priceCommission;
    }
}
