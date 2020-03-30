package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.AccountTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.AccountType}.
 */
public interface AccountTypeService {

    /**
     * Save a accountType.
     *
     * @param accountTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AccountTypeDTO save(AccountTypeDTO accountTypeDTO);

    /**
     * Get all the accountTypes.
     *
     * @return the list of entities.
     */
    List<AccountTypeDTO> findAll();


    /**
     * Get the "id" accountType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountTypeDTO> findOne(Long id);

    /**
     * Delete the "id" accountType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
