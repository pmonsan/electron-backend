package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.ServiceAuthenticationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.ServiceAuthentication}.
 */
public interface ServiceAuthenticationService {

    /**
     * Save a serviceAuthentication.
     *
     * @param serviceAuthenticationDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceAuthenticationDTO save(ServiceAuthenticationDTO serviceAuthenticationDTO);

    /**
     * Get all the serviceAuthentications.
     *
     * @return the list of entities.
     */
    List<ServiceAuthenticationDTO> findAll();


    /**
     * Get the "id" serviceAuthentication.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceAuthenticationDTO> findOne(Long id);

    /**
     * Delete the "id" serviceAuthentication.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
