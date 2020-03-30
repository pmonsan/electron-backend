package com.electron.mfs.pg.limits.service.mapper;

import com.electron.mfs.pg.limits.domain.*;
import com.electron.mfs.pg.limits.service.dto.LimitTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LimitType} and its DTO {@link LimitTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeriodicityMapper.class, LimitMeasureMapper.class})
public interface LimitTypeMapper extends EntityMapper<LimitTypeDTO, LimitType> {

    @Mapping(source = "periodicity.id", target = "periodicityId")
    @Mapping(source = "limitMeasure.id", target = "limitMeasureId")
    LimitTypeDTO toDto(LimitType limitType);

    @Mapping(source = "periodicityId", target = "periodicity")
    @Mapping(source = "limitMeasureId", target = "limitMeasure")
    LimitType toEntity(LimitTypeDTO limitTypeDTO);

    default LimitType fromId(Long id) {
        if (id == null) {
            return null;
        }
        LimitType limitType = new LimitType();
        limitType.setId(id);
        return limitType;
    }
}
