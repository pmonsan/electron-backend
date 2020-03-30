package com.electron.mfs.pg.iam.service.mapper;

import com.electron.mfs.pg.iam.domain.*;
import com.electron.mfs.pg.iam.service.dto.UserConnectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserConnection} and its DTO {@link UserConnectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgUserMapper.class})
public interface UserConnectionMapper extends EntityMapper<UserConnectionDTO, UserConnection> {

    @Mapping(source = "user.id", target = "userId")
    UserConnectionDTO toDto(UserConnection userConnection);

    @Mapping(source = "userId", target = "user")
    UserConnection toEntity(UserConnectionDTO userConnectionDTO);

    default UserConnection fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserConnection userConnection = new UserConnection();
        userConnection.setId(id);
        return userConnection;
    }
}
