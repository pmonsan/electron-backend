package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.PgBatchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgBatch} and its DTO {@link PgBatchDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgBatchMapper extends EntityMapper<PgBatchDTO, PgBatch> {



    default PgBatch fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgBatch pgBatch = new PgBatch();
        pgBatch.setId(id);
        return pgBatch;
    }
}
