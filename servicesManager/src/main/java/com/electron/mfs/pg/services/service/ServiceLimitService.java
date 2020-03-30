package com.electron.mfs.pg.services.service;

import com.electron.mfs.pg.services.service.dto.ServiceLimitDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.services.domain.ServiceLimit}.
 */
public interface ServiceLimitService {

    /**
     * Save a serviceLimit.
     *
     * @param serviceLimitDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceLimitDTO save(ServiceLimitDTO serviceLimitDTO);

    /**
     * Get all the serviceLimits.
     *
     * @return the list of entities.
     */
    List<ServiceLimitDTO> findAll();


    /**
     * Get the "id" serviceLimit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceLimitDTO> findOne(Long id);

    /**
     * Delete the "id" serviceLimit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
