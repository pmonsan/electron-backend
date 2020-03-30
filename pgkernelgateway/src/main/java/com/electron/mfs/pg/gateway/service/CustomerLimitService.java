package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.CustomerLimitDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.CustomerLimit}.
 */
public interface CustomerLimitService {

    /**
     * Save a customerLimit.
     *
     * @param customerLimitDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerLimitDTO save(CustomerLimitDTO customerLimitDTO);

    /**
     * Get all the customerLimits.
     *
     * @return the list of entities.
     */
    List<CustomerLimitDTO> findAll();


    /**
     * Get the "id" customerLimit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerLimitDTO> findOne(Long id);

    /**
     * Delete the "id" customerLimit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
