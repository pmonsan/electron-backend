package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PriceCommissionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PriceCommission}.
 */
public interface PriceCommissionService {

    /**
     * Save a priceCommission.
     *
     * @param priceCommissionDTO the entity to save.
     * @return the persisted entity.
     */
    PriceCommissionDTO save(PriceCommissionDTO priceCommissionDTO);

    /**
     * Get all the priceCommissions.
     *
     * @return the list of entities.
     */
    List<PriceCommissionDTO> findAll();


    /**
     * Get the "id" priceCommission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriceCommissionDTO> findOne(Long id);

    /**
     * Delete the "id" priceCommission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
