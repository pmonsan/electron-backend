package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.LimitTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.LimitType}.
 */
public interface LimitTypeService {

    /**
     * Save a limitType.
     *
     * @param limitTypeDTO the entity to save.
     * @return the persisted entity.
     */
    LimitTypeDTO save(LimitTypeDTO limitTypeDTO);

    /**
     * Get all the limitTypes.
     *
     * @return the list of entities.
     */
    List<LimitTypeDTO> findAll();


    /**
     * Get the "id" limitType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LimitTypeDTO> findOne(Long id);

    /**
     * Delete the "id" limitType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
