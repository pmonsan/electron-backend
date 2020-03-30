package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.SubscriptionPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.SubscriptionPrice}.
 */
public interface SubscriptionPriceService {

    /**
     * Save a subscriptionPrice.
     *
     * @param subscriptionPriceDTO the entity to save.
     * @return the persisted entity.
     */
    SubscriptionPriceDTO save(SubscriptionPriceDTO subscriptionPriceDTO);

    /**
     * Get all the subscriptionPrices.
     *
     * @return the list of entities.
     */
    List<SubscriptionPriceDTO> findAll();


    /**
     * Get the "id" subscriptionPrice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubscriptionPriceDTO> findOne(Long id);

    /**
     * Delete the "id" subscriptionPrice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
