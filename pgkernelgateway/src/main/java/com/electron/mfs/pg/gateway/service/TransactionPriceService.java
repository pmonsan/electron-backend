package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.TransactionPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.TransactionPrice}.
 */
public interface TransactionPriceService {

    /**
     * Save a transactionPrice.
     *
     * @param transactionPriceDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionPriceDTO save(TransactionPriceDTO transactionPriceDTO);

    /**
     * Get all the transactionPrices.
     *
     * @return the list of entities.
     */
    List<TransactionPriceDTO> findAll();


    /**
     * Get the "id" transactionPrice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionPriceDTO> findOne(Long id);

    /**
     * Delete the "id" transactionPrice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
