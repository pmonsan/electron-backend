package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.AccountStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.AccountStatus}.
 */
public interface AccountStatusService {

    /**
     * Save a accountStatus.
     *
     * @param accountStatusDTO the entity to save.
     * @return the persisted entity.
     */
    AccountStatusDTO save(AccountStatusDTO accountStatusDTO);

    /**
     * Get all the accountStatuses.
     *
     * @return the list of entities.
     */
    List<AccountStatusDTO> findAll();


    /**
     * Get the "id" accountStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountStatusDTO> findOne(Long id);

    /**
     * Delete the "id" accountStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
