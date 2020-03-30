package com.electron.mfs.pg.pg8583.service.mapper;

import com.electron.mfs.pg.pg8583.domain.*;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgMessage} and its DTO {@link PgMessageDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgMessageModelMapper.class, PgMessageStatusMapper.class})
public interface PgMessageMapper extends EntityMapper<PgMessageDTO, PgMessage> {

    @Mapping(source = "pgMessageModel.id", target = "pgMessageModelId")
    @Mapping(source = "pgMessageStatus.id", target = "pgMessageStatusId")
    PgMessageDTO toDto(PgMessage pgMessage);

    @Mapping(source = "pgMessageModelId", target = "pgMessageModel")
    @Mapping(source = "pgMessageStatusId", target = "pgMessageStatus")
    PgMessage toEntity(PgMessageDTO pgMessageDTO);

    default PgMessage fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgMessage pgMessage = new PgMessage();
        pgMessage.setId(id);
        return pgMessage;
    }
}
