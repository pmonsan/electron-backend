package com.electron.mfs.pg.customer.service;

import com.electron.mfs.pg.customer.service.dto.CustomerBlacklistDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.customer.domain.CustomerBlacklist}.
 */
public interface CustomerBlacklistService {

    /**
     * Save a customerBlacklist.
     *
     * @param customerBlacklistDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerBlacklistDTO save(CustomerBlacklistDTO customerBlacklistDTO);

    /**
     * Get all the customerBlacklists.
     *
     * @return the list of entities.
     */
    List<CustomerBlacklistDTO> findAll();


    /**
     * Get the "id" customerBlacklist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerBlacklistDTO> findOne(Long id);

    /**
     * Delete the "id" customerBlacklist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
