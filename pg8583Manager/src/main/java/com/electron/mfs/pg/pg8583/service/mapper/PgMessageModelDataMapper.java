package com.electron.mfs.pg.pg8583.service.mapper;

import com.electron.mfs.pg.pg8583.domain.*;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageModelDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgMessageModelData} and its DTO {@link PgMessageModelDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgDataMapper.class, PgMessageModelMapper.class})
public interface PgMessageModelDataMapper extends EntityMapper<PgMessageModelDataDTO, PgMessageModelData> {

    @Mapping(source = "pgData.id", target = "pgDataId")
    @Mapping(source = "pgMessageModel.id", target = "pgMessageModelId")
    PgMessageModelDataDTO toDto(PgMessageModelData pgMessageModelData);

    @Mapping(source = "pgDataId", target = "pgData")
    @Mapping(source = "pgMessageModelId", target = "pgMessageModel")
    PgMessageModelData toEntity(PgMessageModelDataDTO pgMessageModelDataDTO);

    default PgMessageModelData fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgMessageModelData pgMessageModelData = new PgMessageModelData();
        pgMessageModelData.setId(id);
        return pgMessageModelData;
    }
}
