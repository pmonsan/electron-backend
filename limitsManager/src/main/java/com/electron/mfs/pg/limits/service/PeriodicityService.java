package com.electron.mfs.pg.limits.service;

import com.electron.mfs.pg.limits.service.dto.PeriodicityDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.limits.domain.Periodicity}.
 */
public interface PeriodicityService {

    /**
     * Save a periodicity.
     *
     * @param periodicityDTO the entity to save.
     * @return the persisted entity.
     */
    PeriodicityDTO save(PeriodicityDTO periodicityDTO);

    /**
     * Get all the periodicities.
     *
     * @return the list of entities.
     */
    List<PeriodicityDTO> findAll();


    /**
     * Get the "id" periodicity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeriodicityDTO> findOne(Long id);

    /**
     * Delete the "id" periodicity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
