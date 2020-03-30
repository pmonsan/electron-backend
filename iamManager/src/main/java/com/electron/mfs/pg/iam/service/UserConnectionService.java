package com.electron.mfs.pg.iam.service;

import com.electron.mfs.pg.iam.service.dto.UserConnectionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.iam.domain.UserConnection}.
 */
public interface UserConnectionService {

    /**
     * Save a userConnection.
     *
     * @param userConnectionDTO the entity to save.
     * @return the persisted entity.
     */
    UserConnectionDTO save(UserConnectionDTO userConnectionDTO);

    /**
     * Get all the userConnections.
     *
     * @return the list of entities.
     */
    List<UserConnectionDTO> findAll();


    /**
     * Get the "id" userConnection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserConnectionDTO> findOne(Long id);

    /**
     * Delete the "id" userConnection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
