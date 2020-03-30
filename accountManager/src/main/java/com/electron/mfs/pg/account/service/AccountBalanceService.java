package com.electron.mfs.pg.account.service;

import com.electron.mfs.pg.account.service.dto.AccountBalanceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.account.domain.AccountBalance}.
 */
public interface AccountBalanceService {

    /**
     * Save a accountBalance.
     *
     * @param accountBalanceDTO the entity to save.
     * @return the persisted entity.
     */
    AccountBalanceDTO save(AccountBalanceDTO accountBalanceDTO);

    /**
     * Get all the accountBalances.
     *
     * @return the list of entities.
     */
    List<AccountBalanceDTO> findAll();


    /**
     * Get the "id" accountBalance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountBalanceDTO> findOne(Long id);

    /**
     * Delete the "id" accountBalance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
