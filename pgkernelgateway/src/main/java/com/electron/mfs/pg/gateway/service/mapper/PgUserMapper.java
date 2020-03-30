package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PgUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgUser} and its DTO {@link PgUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface PgUserMapper extends EntityMapper<PgUserDTO, PgUser> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    PgUserDTO toDto(PgUser pgUser);

    @Mapping(source = "userProfileId", target = "userProfile")
    PgUser toEntity(PgUserDTO pgUserDTO);

    default PgUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgUser pgUser = new PgUser();
        pgUser.setId(id);
        return pgUser;
    }
}
