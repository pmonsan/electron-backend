package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.LoanInstalmentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.LoanInstalment}.
 */
public interface LoanInstalmentService {

    /**
     * Save a loanInstalment.
     *
     * @param loanInstalmentDTO the entity to save.
     * @return the persisted entity.
     */
    LoanInstalmentDTO save(LoanInstalmentDTO loanInstalmentDTO);

    /**
     * Get all the loanInstalments.
     *
     * @return the list of entities.
     */
    List<LoanInstalmentDTO> findAll();


    /**
     * Get the "id" loanInstalment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoanInstalmentDTO> findOne(Long id);

    /**
     * Delete the "id" loanInstalment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
