package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.TransactionTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.TransactionType}.
 */
public interface TransactionTypeService {

    /**
     * Save a transactionType.
     *
     * @param transactionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionTypeDTO save(TransactionTypeDTO transactionTypeDTO);

    /**
     * Get all the transactionTypes.
     *
     * @return the list of entities.
     */
    List<TransactionTypeDTO> findAll();


    /**
     * Get the "id" transactionType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionTypeDTO> findOne(Long id);

    /**
     * Delete the "id" transactionType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
