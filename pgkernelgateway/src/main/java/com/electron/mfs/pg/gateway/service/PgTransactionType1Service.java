package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PgTransactionType1DTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PgTransactionType1}.
 */
public interface PgTransactionType1Service {

    /**
     * Save a pgTransactionType1.
     *
     * @param pgTransactionType1DTO the entity to save.
     * @return the persisted entity.
     */
    PgTransactionType1DTO save(PgTransactionType1DTO pgTransactionType1DTO);

    /**
     * Get all the pgTransactionType1S.
     *
     * @return the list of entities.
     */
    List<PgTransactionType1DTO> findAll();


    /**
     * Get the "id" pgTransactionType1.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgTransactionType1DTO> findOne(Long id);

    /**
     * Delete the "id" pgTransactionType1.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
