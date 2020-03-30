package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.DetailTransactionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.DetailTransaction}.
 */
public interface DetailTransactionService {

    /**
     * Save a detailTransaction.
     *
     * @param detailTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    DetailTransactionDTO save(DetailTransactionDTO detailTransactionDTO);

    /**
     * Get all the detailTransactions.
     *
     * @return the list of entities.
     */
    List<DetailTransactionDTO> findAll();


    /**
     * Get the "id" detailTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" detailTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
