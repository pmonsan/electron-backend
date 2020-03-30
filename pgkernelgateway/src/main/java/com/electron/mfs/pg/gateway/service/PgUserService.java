package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PgUserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PgUser}.
 */
public interface PgUserService {

    /**
     * Save a pgUser.
     *
     * @param pgUserDTO the entity to save.
     * @return the persisted entity.
     */
    PgUserDTO save(PgUserDTO pgUserDTO);

    /**
     * Get all the pgUsers.
     *
     * @return the list of entities.
     */
    List<PgUserDTO> findAll();


    /**
     * Get the "id" pgUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgUserDTO> findOne(Long id);

    /**
     * Delete the "id" pgUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
