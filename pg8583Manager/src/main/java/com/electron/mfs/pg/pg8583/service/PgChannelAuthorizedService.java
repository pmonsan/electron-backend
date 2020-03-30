package com.electron.mfs.pg.pg8583.service;

import com.electron.mfs.pg.pg8583.service.dto.PgChannelAuthorizedDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583.domain.PgChannelAuthorized}.
 */
public interface PgChannelAuthorizedService {

    /**
     * Save a pgChannelAuthorized.
     *
     * @param pgChannelAuthorizedDTO the entity to save.
     * @return the persisted entity.
     */
    PgChannelAuthorizedDTO save(PgChannelAuthorizedDTO pgChannelAuthorizedDTO);

    /**
     * Get all the pgChannelAuthorizeds.
     *
     * @return the list of entities.
     */
    List<PgChannelAuthorizedDTO> findAll();


    /**
     * Get the "id" pgChannelAuthorized.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgChannelAuthorizedDTO> findOne(Long id);

    /**
     * Delete the "id" pgChannelAuthorized.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
