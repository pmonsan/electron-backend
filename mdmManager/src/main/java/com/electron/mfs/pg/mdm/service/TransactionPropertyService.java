package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.TransactionPropertyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.TransactionProperty}.
 */
public interface TransactionPropertyService {

    /**
     * Save a transactionProperty.
     *
     * @param transactionPropertyDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionPropertyDTO save(TransactionPropertyDTO transactionPropertyDTO);

    /**
     * Get all the transactionProperties.
     *
     * @return the list of entities.
     */
    List<TransactionPropertyDTO> findAll();


    /**
     * Get the "id" transactionProperty.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionPropertyDTO> findOne(Long id);

    /**
     * Delete the "id" transactionProperty.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
