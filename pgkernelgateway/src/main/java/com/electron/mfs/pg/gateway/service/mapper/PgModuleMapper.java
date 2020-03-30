package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PgModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgModule} and its DTO {@link PgModuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgModuleMapper extends EntityMapper<PgModuleDTO, PgModule> {



    default PgModule fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgModule pgModule = new PgModule();
        pgModule.setId(id);
        return pgModule;
    }
}
