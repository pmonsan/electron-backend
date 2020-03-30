package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.CustomerSubscriptionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.CustomerSubscription}.
 */
public interface CustomerSubscriptionService {

    /**
     * Save a customerSubscription.
     *
     * @param customerSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerSubscriptionDTO save(CustomerSubscriptionDTO customerSubscriptionDTO);

    /**
     * Get all the customerSubscriptions.
     *
     * @return the list of entities.
     */
    List<CustomerSubscriptionDTO> findAll();


    /**
     * Get the "id" customerSubscription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerSubscriptionDTO> findOne(Long id);

    /**
     * Delete the "id" customerSubscription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
