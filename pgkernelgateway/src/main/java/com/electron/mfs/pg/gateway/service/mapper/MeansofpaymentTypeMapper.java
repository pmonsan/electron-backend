package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeansofpaymentType} and its DTO {@link MeansofpaymentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MeansofpaymentTypeMapper extends EntityMapper<MeansofpaymentTypeDTO, MeansofpaymentType> {



    default MeansofpaymentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeansofpaymentType meansofpaymentType = new MeansofpaymentType();
        meansofpaymentType.setId(id);
        return meansofpaymentType;
    }
}
