package com.electron.mfs.pg.services.service;

import com.electron.mfs.pg.services.service.dto.PgServiceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.services.domain.PgService}.
 */
public interface PgServiceService {

    /**
     * Save a pgService.
     *
     * @param pgServiceDTO the entity to save.
     * @return the persisted entity.
     */
    PgServiceDTO save(PgServiceDTO pgServiceDTO);

    /**
     * Get all the pgServices.
     *
     * @return the list of entities.
     */
    List<PgServiceDTO> findAll();


    /**
     * Get the "id" pgService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgServiceDTO> findOne(Long id);

    /**
     * Delete the "id" pgService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
