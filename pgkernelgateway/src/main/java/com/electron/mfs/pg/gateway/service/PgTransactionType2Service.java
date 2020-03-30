package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PgTransactionType2DTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PgTransactionType2}.
 */
public interface PgTransactionType2Service {

    /**
     * Save a pgTransactionType2.
     *
     * @param pgTransactionType2DTO the entity to save.
     * @return the persisted entity.
     */
    PgTransactionType2DTO save(PgTransactionType2DTO pgTransactionType2DTO);

    /**
     * Get all the pgTransactionType2S.
     *
     * @return the list of entities.
     */
    List<PgTransactionType2DTO> findAll();


    /**
     * Get the "id" pgTransactionType2.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgTransactionType2DTO> findOne(Long id);

    /**
     * Delete the "id" pgTransactionType2.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
