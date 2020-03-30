package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.TransactionGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.TransactionGroup}.
 */
public interface TransactionGroupService {

    /**
     * Save a transactionGroup.
     *
     * @param transactionGroupDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionGroupDTO save(TransactionGroupDTO transactionGroupDTO);

    /**
     * Get all the transactionGroups.
     *
     * @return the list of entities.
     */
    List<TransactionGroupDTO> findAll();


    /**
     * Get the "id" transactionGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionGroupDTO> findOne(Long id);

    /**
     * Delete the "id" transactionGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
