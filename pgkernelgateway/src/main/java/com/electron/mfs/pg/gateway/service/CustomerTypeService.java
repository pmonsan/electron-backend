package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.CustomerTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.CustomerType}.
 */
public interface CustomerTypeService {

    /**
     * Save a customerType.
     *
     * @param customerTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerTypeDTO save(CustomerTypeDTO customerTypeDTO);

    /**
     * Get all the customerTypes.
     *
     * @return the list of entities.
     */
    List<CustomerTypeDTO> findAll();


    /**
     * Get the "id" customerType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerTypeDTO> findOne(Long id);

    /**
     * Delete the "id" customerType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
