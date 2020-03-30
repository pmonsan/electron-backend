package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PartnerTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartnerType} and its DTO {@link PartnerTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PartnerTypeMapper extends EntityMapper<PartnerTypeDTO, PartnerType> {



    default PartnerType fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartnerType partnerType = new PartnerType();
        partnerType.setId(id);
        return partnerType;
    }
}
