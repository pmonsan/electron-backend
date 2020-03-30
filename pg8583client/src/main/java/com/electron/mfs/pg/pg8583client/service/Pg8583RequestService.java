package com.electron.mfs.pg.pg8583client.service;

import com.electron.mfs.pg.pg8583client.service.dto.Pg8583RequestDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583client.domain.Pg8583Request}.
 */
public interface Pg8583RequestService {

    /**
     * Save a pg8583Request.
     *
     * @param pg8583RequestDTO the entity to save.
     * @return the persisted entity.
     */
    Pg8583RequestDTO save(Pg8583RequestDTO pg8583RequestDTO);

    /**
     * Get all the pg8583Requests.
     *
     * @return the list of entities.
     */
    List<Pg8583RequestDTO> findAll();


    /**
     * Get the "id" pg8583Request.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pg8583RequestDTO> findOne(Long id);

    /**
     * Delete the "id" pg8583Request.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
