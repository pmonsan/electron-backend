package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PgAccountDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PgAccount}.
 */
public interface PgAccountService {

    /**
     * Save a pgAccount.
     *
     * @param pgAccountDTO the entity to save.
     * @return the persisted entity.
     */
    PgAccountDTO save(PgAccountDTO pgAccountDTO);

    /**
     * Get all the pgAccounts.
     *
     * @return the list of entities.
     */
    List<PgAccountDTO> findAll();


    /**
     * Get the "id" pgAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgAccountDTO> findOne(Long id);

    /**
     * Delete the "id" pgAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
