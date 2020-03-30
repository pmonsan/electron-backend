package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.UserProfileDataDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.UserProfileData}.
 */
public interface UserProfileDataService {

    /**
     * Save a userProfileData.
     *
     * @param userProfileDataDTO the entity to save.
     * @return the persisted entity.
     */
    UserProfileDataDTO save(UserProfileDataDTO userProfileDataDTO);

    /**
     * Get all the userProfileData.
     *
     * @return the list of entities.
     */
    List<UserProfileDataDTO> findAll();


    /**
     * Get the "id" userProfileData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserProfileDataDTO> findOne(Long id);

    /**
     * Delete the "id" userProfileData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
