package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PgMessageModelDataDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PgMessageModelData}.
 */
public interface PgMessageModelDataService {

    /**
     * Save a pgMessageModelData.
     *
     * @param pgMessageModelDataDTO the entity to save.
     * @return the persisted entity.
     */
    PgMessageModelDataDTO save(PgMessageModelDataDTO pgMessageModelDataDTO);

    /**
     * Get all the pgMessageModelData.
     *
     * @return the list of entities.
     */
    List<PgMessageModelDataDTO> findAll();


    /**
     * Get the "id" pgMessageModelData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgMessageModelDataDTO> findOne(Long id);

    /**
     * Delete the "id" pgMessageModelData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
