package com.electron.mfs.pg.feescommission.service;

import com.electron.mfs.pg.feescommission.service.dto.PriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.feescommission.domain.Price}.
 */
public interface PriceService {

    /**
     * Save a price.
     *
     * @param priceDTO the entity to save.
     * @return the persisted entity.
     */
    PriceDTO save(PriceDTO priceDTO);

    /**
     * Get all the prices.
     *
     * @return the list of entities.
     */
    List<PriceDTO> findAll();


    /**
     * Get the "id" price.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriceDTO> findOne(Long id);

    /**
     * Delete the "id" price.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
