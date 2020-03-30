package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.Pg8583CallbackDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.Pg8583Callback}.
 */
public interface Pg8583CallbackService {

    /**
     * Save a pg8583Callback.
     *
     * @param pg8583CallbackDTO the entity to save.
     * @return the persisted entity.
     */
    Pg8583CallbackDTO save(Pg8583CallbackDTO pg8583CallbackDTO);

    /**
     * Get all the pg8583Callbacks.
     *
     * @return the list of entities.
     */
    List<Pg8583CallbackDTO> findAll();


    /**
     * Get the "id" pg8583Callback.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pg8583CallbackDTO> findOne(Long id);

    /**
     * Delete the "id" pg8583Callback.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
