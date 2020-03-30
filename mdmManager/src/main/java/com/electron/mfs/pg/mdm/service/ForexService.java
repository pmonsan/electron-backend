package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.ForexDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.Forex}.
 */
public interface ForexService {

    /**
     * Save a forex.
     *
     * @param forexDTO the entity to save.
     * @return the persisted entity.
     */
    ForexDTO save(ForexDTO forexDTO);

    /**
     * Get all the forexes.
     *
     * @return the list of entities.
     */
    List<ForexDTO> findAll();


    /**
     * Get the "id" forex.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ForexDTO> findOne(Long id);

    /**
     * Delete the "id" forex.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
