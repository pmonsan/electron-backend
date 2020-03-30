package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PgDetailMessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgDetailMessage} and its DTO {@link PgDetailMessageDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgDataMapper.class, PgMessageMapper.class})
public interface PgDetailMessageMapper extends EntityMapper<PgDetailMessageDTO, PgDetailMessage> {

    @Mapping(source = "pgData.id", target = "pgDataId")
    @Mapping(source = "pgMessage.id", target = "pgMessageId")
    PgDetailMessageDTO toDto(PgDetailMessage pgDetailMessage);

    @Mapping(source = "pgDataId", target = "pgData")
    @Mapping(source = "pgMessageId", target = "pgMessage")
    PgDetailMessage toEntity(PgDetailMessageDTO pgDetailMessageDTO);

    default PgDetailMessage fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgDetailMessage pgDetailMessage = new PgDetailMessage();
        pgDetailMessage.setId(id);
        return pgDetailMessage;
    }
}
