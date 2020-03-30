package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.AccountFeatureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.AccountFeature}.
 */
public interface AccountFeatureService {

    /**
     * Save a accountFeature.
     *
     * @param accountFeatureDTO the entity to save.
     * @return the persisted entity.
     */
    AccountFeatureDTO save(AccountFeatureDTO accountFeatureDTO);

    /**
     * Get all the accountFeatures.
     *
     * @return the list of entities.
     */
    List<AccountFeatureDTO> findAll();


    /**
     * Get the "id" accountFeature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountFeatureDTO> findOne(Long id);

    /**
     * Delete the "id" accountFeature.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
