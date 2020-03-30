package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PgChannelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PgChannel}.
 */
public interface PgChannelService {

    /**
     * Save a pgChannel.
     *
     * @param pgChannelDTO the entity to save.
     * @return the persisted entity.
     */
    PgChannelDTO save(PgChannelDTO pgChannelDTO);

    /**
     * Get all the pgChannels.
     *
     * @return the list of entities.
     */
    List<PgChannelDTO> findAll();


    /**
     * Get the "id" pgChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgChannelDTO> findOne(Long id);

    /**
     * Delete the "id" pgChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
