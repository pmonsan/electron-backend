package com.electron.mfs.pg.pg8583client.service;

import com.electron.mfs.pg.pg8583client.service.dto.Pg8583StatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583client.domain.Pg8583Status}.
 */
public interface Pg8583StatusService {

    /**
     * Save a pg8583Status.
     *
     * @param pg8583StatusDTO the entity to save.
     * @return the persisted entity.
     */
    Pg8583StatusDTO save(Pg8583StatusDTO pg8583StatusDTO);

    /**
     * Get all the pg8583Statuses.
     *
     * @return the list of entities.
     */
    List<Pg8583StatusDTO> findAll();


    /**
     * Get the "id" pg8583Status.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pg8583StatusDTO> findOne(Long id);

    /**
     * Delete the "id" pg8583Status.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
