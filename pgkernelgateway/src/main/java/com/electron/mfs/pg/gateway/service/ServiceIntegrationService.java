package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.ServiceIntegrationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.ServiceIntegration}.
 */
public interface ServiceIntegrationService {

    /**
     * Save a serviceIntegration.
     *
     * @param serviceIntegrationDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceIntegrationDTO save(ServiceIntegrationDTO serviceIntegrationDTO);

    /**
     * Get all the serviceIntegrations.
     *
     * @return the list of entities.
     */
    List<ServiceIntegrationDTO> findAll();


    /**
     * Get the "id" serviceIntegration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceIntegrationDTO> findOne(Long id);

    /**
     * Delete the "id" serviceIntegration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
