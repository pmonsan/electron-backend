package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.ActivityAreaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.ActivityArea}.
 */
public interface ActivityAreaService {

    /**
     * Save a activityArea.
     *
     * @param activityAreaDTO the entity to save.
     * @return the persisted entity.
     */
    ActivityAreaDTO save(ActivityAreaDTO activityAreaDTO);

    /**
     * Get all the activityAreas.
     *
     * @return the list of entities.
     */
    List<ActivityAreaDTO> findAll();


    /**
     * Get the "id" activityArea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityAreaDTO> findOne(Long id);

    /**
     * Delete the "id" activityArea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
