package com.electron.mfs.pg.pg8583.service;

import com.electron.mfs.pg.pg8583.service.dto.PgDetailMessageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583.domain.PgDetailMessage}.
 */
public interface PgDetailMessageService {

    /**
     * Save a pgDetailMessage.
     *
     * @param pgDetailMessageDTO the entity to save.
     * @return the persisted entity.
     */
    PgDetailMessageDTO save(PgDetailMessageDTO pgDetailMessageDTO);

    /**
     * Get all the pgDetailMessages.
     *
     * @return the list of entities.
     */
    List<PgDetailMessageDTO> findAll();


    /**
     * Get the "id" pgDetailMessage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgDetailMessageDTO> findOne(Long id);

    /**
     * Delete the "id" pgDetailMessage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
