package com.electron.mfs.pg.pg8583.service.mapper;

import com.electron.mfs.pg.pg8583.domain.*;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgMessageModel} and its DTO {@link PgMessageModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgMessageModelMapper extends EntityMapper<PgMessageModelDTO, PgMessageModel> {



    default PgMessageModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgMessageModel pgMessageModel = new PgMessageModel();
        pgMessageModel.setId(id);
        return pgMessageModel;
    }
}
