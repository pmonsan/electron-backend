package com.electron.mfs.pg.limits.service.mapper;

import com.electron.mfs.pg.limits.domain.*;
import com.electron.mfs.pg.limits.service.dto.LimitMeasureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LimitMeasure} and its DTO {@link LimitMeasureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LimitMeasureMapper extends EntityMapper<LimitMeasureDTO, LimitMeasure> {



    default LimitMeasure fromId(Long id) {
        if (id == null) {
            return null;
        }
        LimitMeasure limitMeasure = new LimitMeasure();
        limitMeasure.setId(id);
        return limitMeasure;
    }
}
