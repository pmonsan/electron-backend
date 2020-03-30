package com.electron.mfs.pg.transactions.service;

import com.electron.mfs.pg.transactions.service.dto.TransactionCommissionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.transactions.domain.TransactionCommission}.
 */
public interface TransactionCommissionService {

    /**
     * Save a transactionCommission.
     *
     * @param transactionCommissionDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionCommissionDTO save(TransactionCommissionDTO transactionCommissionDTO);

    /**
     * Get all the transactionCommissions.
     *
     * @return the list of entities.
     */
    List<TransactionCommissionDTO> findAll();


    /**
     * Get the "id" transactionCommission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionCommissionDTO> findOne(Long id);

    /**
     * Delete the "id" transactionCommission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
