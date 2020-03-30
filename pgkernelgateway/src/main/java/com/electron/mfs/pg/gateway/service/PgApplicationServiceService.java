package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PgApplicationServiceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PgApplicationService}.
 */
public interface PgApplicationServiceService {

    /**
     * Save a pgApplicationService.
     *
     * @param pgApplicationServiceDTO the entity to save.
     * @return the persisted entity.
     */
    PgApplicationServiceDTO save(PgApplicationServiceDTO pgApplicationServiceDTO);

    /**
     * Get all the pgApplicationServices.
     *
     * @return the list of entities.
     */
    List<PgApplicationServiceDTO> findAll();


    /**
     * Get the "id" pgApplicationService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgApplicationServiceDTO> findOne(Long id);

    /**
     * Delete the "id" pgApplicationService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
