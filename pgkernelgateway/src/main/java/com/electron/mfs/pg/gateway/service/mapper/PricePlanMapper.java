package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PricePlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PricePlan} and its DTO {@link PricePlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PricePlanMapper extends EntityMapper<PricePlanDTO, PricePlan> {



    default PricePlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        PricePlan pricePlan = new PricePlan();
        pricePlan.setId(id);
        return pricePlan;
    }
}
