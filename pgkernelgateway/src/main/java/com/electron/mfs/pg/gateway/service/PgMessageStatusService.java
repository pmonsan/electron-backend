package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PgMessageStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PgMessageStatus}.
 */
public interface PgMessageStatusService {

    /**
     * Save a pgMessageStatus.
     *
     * @param pgMessageStatusDTO the entity to save.
     * @return the persisted entity.
     */
    PgMessageStatusDTO save(PgMessageStatusDTO pgMessageStatusDTO);

    /**
     * Get all the pgMessageStatuses.
     *
     * @return the list of entities.
     */
    List<PgMessageStatusDTO> findAll();


    /**
     * Get the "id" pgMessageStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgMessageStatusDTO> findOne(Long id);

    /**
     * Delete the "id" pgMessageStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
