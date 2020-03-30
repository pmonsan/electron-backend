package com.electron.mfs.pg.transactions.service;

import com.electron.mfs.pg.transactions.service.dto.TransactionInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.transactions.domain.TransactionInfo}.
 */
public interface TransactionInfoService {

    /**
     * Save a transactionInfo.
     *
     * @param transactionInfoDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionInfoDTO save(TransactionInfoDTO transactionInfoDTO);

    /**
     * Get all the transactionInfos.
     *
     * @return the list of entities.
     */
    List<TransactionInfoDTO> findAll();


    /**
     * Get the "id" transactionInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionInfoDTO> findOne(Long id);

    /**
     * Delete the "id" transactionInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
