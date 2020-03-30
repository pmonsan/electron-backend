package com.electron.mfs.pg.pg8583.service;

import com.electron.mfs.pg.pg8583.service.dto.PgMessageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583.domain.PgMessage}.
 */
public interface PgMessageService {

    /**
     * Save a pgMessage.
     *
     * @param pgMessageDTO the entity to save.
     * @return the persisted entity.
     */
    PgMessageDTO save(PgMessageDTO pgMessageDTO);

    /**
     * Get all the pgMessages.
     *
     * @return the list of entities.
     */
    List<PgMessageDTO> findAll();


    /**
     * Get the "id" pgMessage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgMessageDTO> findOne(Long id);

    /**
     * Delete the "id" pgMessage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
