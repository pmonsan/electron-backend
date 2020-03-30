package com.electron.mfs.pg.pg8583.service;

import com.electron.mfs.pg.pg8583.service.dto.PgDataDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583.domain.PgData}.
 */
public interface PgDataService {

    /**
     * Save a pgData.
     *
     * @param pgDataDTO the entity to save.
     * @return the persisted entity.
     */
    PgDataDTO save(PgDataDTO pgDataDTO);

    /**
     * Get all the pgData.
     *
     * @return the list of entities.
     */
    List<PgDataDTO> findAll();


    /**
     * Get the "id" pgData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgDataDTO> findOne(Long id);

    /**
     * Delete the "id" pgData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
