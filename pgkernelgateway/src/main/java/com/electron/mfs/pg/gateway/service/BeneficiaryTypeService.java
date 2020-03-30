package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.BeneficiaryTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.BeneficiaryType}.
 */
public interface BeneficiaryTypeService {

    /**
     * Save a beneficiaryType.
     *
     * @param beneficiaryTypeDTO the entity to save.
     * @return the persisted entity.
     */
    BeneficiaryTypeDTO save(BeneficiaryTypeDTO beneficiaryTypeDTO);

    /**
     * Get all the beneficiaryTypes.
     *
     * @return the list of entities.
     */
    List<BeneficiaryTypeDTO> findAll();


    /**
     * Get the "id" beneficiaryType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BeneficiaryTypeDTO> findOne(Long id);

    /**
     * Delete the "id" beneficiaryType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
