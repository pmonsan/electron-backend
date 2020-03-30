package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.ServiceChannelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.ServiceChannel}.
 */
public interface ServiceChannelService {

    /**
     * Save a serviceChannel.
     *
     * @param serviceChannelDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceChannelDTO save(ServiceChannelDTO serviceChannelDTO);

    /**
     * Get all the serviceChannels.
     *
     * @return the list of entities.
     */
    List<ServiceChannelDTO> findAll();


    /**
     * Get the "id" serviceChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceChannelDTO> findOne(Long id);

    /**
     * Delete the "id" serviceChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
