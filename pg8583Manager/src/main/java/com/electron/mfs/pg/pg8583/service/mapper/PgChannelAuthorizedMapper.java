package com.electron.mfs.pg.pg8583.service.mapper;

import com.electron.mfs.pg.pg8583.domain.*;
import com.electron.mfs.pg.pg8583.service.dto.PgChannelAuthorizedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgChannelAuthorized} and its DTO {@link PgChannelAuthorizedDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgChannelMapper.class})
public interface PgChannelAuthorizedMapper extends EntityMapper<PgChannelAuthorizedDTO, PgChannelAuthorized> {

    @Mapping(source = "pgChannel.id", target = "pgChannelId")
    PgChannelAuthorizedDTO toDto(PgChannelAuthorized pgChannelAuthorized);

    @Mapping(source = "pgChannelId", target = "pgChannel")
    PgChannelAuthorized toEntity(PgChannelAuthorizedDTO pgChannelAuthorizedDTO);

    default PgChannelAuthorized fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgChannelAuthorized pgChannelAuthorized = new PgChannelAuthorized();
        pgChannelAuthorized.setId(id);
        return pgChannelAuthorized;
    }
}
