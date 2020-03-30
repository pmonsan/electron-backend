package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.BeneficiaryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.Beneficiary}.
 */
public interface BeneficiaryService {

    /**
     * Save a beneficiary.
     *
     * @param beneficiaryDTO the entity to save.
     * @return the persisted entity.
     */
    BeneficiaryDTO save(BeneficiaryDTO beneficiaryDTO);

    /**
     * Get all the beneficiaries.
     *
     * @return the list of entities.
     */
    List<BeneficiaryDTO> findAll();


    /**
     * Get the "id" beneficiary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BeneficiaryDTO> findOne(Long id);

    /**
     * Delete the "id" beneficiary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
