package com.electron.mfs.pg.pg8583.service;

import com.electron.mfs.pg.pg8583.service.dto.PgApplicationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583.domain.PgApplication}.
 */
public interface PgApplicationService {

    /**
     * Save a pgApplication.
     *
     * @param pgApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    PgApplicationDTO save(PgApplicationDTO pgApplicationDTO);

    /**
     * Get all the pgApplications.
     *
     * @return the list of entities.
     */
    List<PgApplicationDTO> findAll();


    /**
     * Get the "id" pgApplication.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgApplicationDTO> findOne(Long id);

    /**
     * Delete the "id" pgApplication.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
