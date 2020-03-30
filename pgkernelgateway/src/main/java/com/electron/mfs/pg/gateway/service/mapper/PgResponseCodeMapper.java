package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PgResponseCodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgResponseCode} and its DTO {@link PgResponseCodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgResponseCodeMapper extends EntityMapper<PgResponseCodeDTO, PgResponseCode> {



    default PgResponseCode fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgResponseCode pgResponseCode = new PgResponseCode();
        pgResponseCode.setId(id);
        return pgResponseCode;
    }
}
