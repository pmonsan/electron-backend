package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.PgDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgData} and its DTO {@link PgDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgDataMapper extends EntityMapper<PgDataDTO, PgData> {



    default PgData fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgData pgData = new PgData();
        pgData.setId(id);
        return pgData;
    }
}
