package com.electron.mfs.pg.feescommission.service;

import com.electron.mfs.pg.feescommission.service.dto.PricePlanDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.feescommission.domain.PricePlan}.
 */
public interface PricePlanService {

    /**
     * Save a pricePlan.
     *
     * @param pricePlanDTO the entity to save.
     * @return the persisted entity.
     */
    PricePlanDTO save(PricePlanDTO pricePlanDTO);

    /**
     * Get all the pricePlans.
     *
     * @return the list of entities.
     */
    List<PricePlanDTO> findAll();


    /**
     * Get the "id" pricePlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PricePlanDTO> findOne(Long id);

    /**
     * Delete the "id" pricePlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
