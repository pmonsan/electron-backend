package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.PgBatchDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.PgBatch}.
 */
public interface PgBatchService {

    /**
     * Save a pgBatch.
     *
     * @param pgBatchDTO the entity to save.
     * @return the persisted entity.
     */
    PgBatchDTO save(PgBatchDTO pgBatchDTO);

    /**
     * Get all the pgBatches.
     *
     * @return the list of entities.
     */
    List<PgBatchDTO> findAll();


    /**
     * Get the "id" pgBatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgBatchDTO> findOne(Long id);

    /**
     * Delete the "id" pgBatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
