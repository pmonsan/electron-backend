package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.UserProfileDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserProfileData} and its DTO {@link UserProfileDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface UserProfileDataMapper extends EntityMapper<UserProfileDataDTO, UserProfileData> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    UserProfileDataDTO toDto(UserProfileData userProfileData);

    @Mapping(source = "userProfileId", target = "userProfile")
    UserProfileData toEntity(UserProfileDataDTO userProfileDataDTO);

    default UserProfileData fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserProfileData userProfileData = new UserProfileData();
        userProfileData.setId(id);
        return userProfileData;
    }
}
