package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.TransactionStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.TransactionStatus}.
 */
public interface TransactionStatusService {

    /**
     * Save a transactionStatus.
     *
     * @param transactionStatusDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionStatusDTO save(TransactionStatusDTO transactionStatusDTO);

    /**
     * Get all the transactionStatuses.
     *
     * @return the list of entities.
     */
    List<TransactionStatusDTO> findAll();


    /**
     * Get the "id" transactionStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionStatusDTO> findOne(Long id);

    /**
     * Delete the "id" transactionStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
