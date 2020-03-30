package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PgMessageStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgMessageStatus} and its DTO {@link PgMessageStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgMessageStatusMapper extends EntityMapper<PgMessageStatusDTO, PgMessageStatus> {



    default PgMessageStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgMessageStatus pgMessageStatus = new PgMessageStatus();
        pgMessageStatus.setId(id);
        return pgMessageStatus;
    }
}
