package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.BeneficiaryRelationshipDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.BeneficiaryRelationship}.
 */
public interface BeneficiaryRelationshipService {

    /**
     * Save a beneficiaryRelationship.
     *
     * @param beneficiaryRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    BeneficiaryRelationshipDTO save(BeneficiaryRelationshipDTO beneficiaryRelationshipDTO);

    /**
     * Get all the beneficiaryRelationships.
     *
     * @return the list of entities.
     */
    List<BeneficiaryRelationshipDTO> findAll();


    /**
     * Get the "id" beneficiaryRelationship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BeneficiaryRelationshipDTO> findOne(Long id);

    /**
     * Delete the "id" beneficiaryRelationship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
