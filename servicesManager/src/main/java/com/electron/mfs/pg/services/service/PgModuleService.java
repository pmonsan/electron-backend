package com.electron.mfs.pg.services.service;

import com.electron.mfs.pg.services.service.dto.PgModuleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.services.domain.PgModule}.
 */
public interface PgModuleService {

    /**
     * Save a pgModule.
     *
     * @param pgModuleDTO the entity to save.
     * @return the persisted entity.
     */
    PgModuleDTO save(PgModuleDTO pgModuleDTO);

    /**
     * Get all the pgModules.
     *
     * @return the list of entities.
     */
    List<PgModuleDTO> findAll();


    /**
     * Get the "id" pgModule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgModuleDTO> findOne(Long id);

    /**
     * Delete the "id" pgModule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
