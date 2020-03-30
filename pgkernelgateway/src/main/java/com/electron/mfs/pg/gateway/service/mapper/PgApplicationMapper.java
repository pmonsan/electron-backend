package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PgApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgApplication} and its DTO {@link PgApplicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgApplicationMapper extends EntityMapper<PgApplicationDTO, PgApplication> {



    default PgApplication fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgApplication pgApplication = new PgApplication();
        pgApplication.setId(id);
        return pgApplication;
    }
}
