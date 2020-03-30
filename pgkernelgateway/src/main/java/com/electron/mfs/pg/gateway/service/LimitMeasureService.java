package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.LimitMeasureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.LimitMeasure}.
 */
public interface LimitMeasureService {

    /**
     * Save a limitMeasure.
     *
     * @param limitMeasureDTO the entity to save.
     * @return the persisted entity.
     */
    LimitMeasureDTO save(LimitMeasureDTO limitMeasureDTO);

    /**
     * Get all the limitMeasures.
     *
     * @return the list of entities.
     */
    List<LimitMeasureDTO> findAll();


    /**
     * Get the "id" limitMeasure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LimitMeasureDTO> findOne(Long id);

    /**
     * Delete the "id" limitMeasure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
