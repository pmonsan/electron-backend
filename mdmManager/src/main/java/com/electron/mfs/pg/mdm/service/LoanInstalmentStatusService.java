package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.LoanInstalmentStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.LoanInstalmentStatus}.
 */
public interface LoanInstalmentStatusService {

    /**
     * Save a loanInstalmentStatus.
     *
     * @param loanInstalmentStatusDTO the entity to save.
     * @return the persisted entity.
     */
    LoanInstalmentStatusDTO save(LoanInstalmentStatusDTO loanInstalmentStatusDTO);

    /**
     * Get all the loanInstalmentStatuses.
     *
     * @return the list of entities.
     */
    List<LoanInstalmentStatusDTO> findAll();


    /**
     * Get the "id" loanInstalmentStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoanInstalmentStatusDTO> findOne(Long id);

    /**
     * Delete the "id" loanInstalmentStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
