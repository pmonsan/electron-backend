package com.electron.mfs.pg.pg8583.service;

import com.electron.mfs.pg.pg8583.service.dto.PgMessageModelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583.domain.PgMessageModel}.
 */
public interface PgMessageModelService {

    /**
     * Save a pgMessageModel.
     *
     * @param pgMessageModelDTO the entity to save.
     * @return the persisted entity.
     */
    PgMessageModelDTO save(PgMessageModelDTO pgMessageModelDTO);

    /**
     * Get all the pgMessageModels.
     *
     * @return the list of entities.
     */
    List<PgMessageModelDTO> findAll();


    /**
     * Get the "id" pgMessageModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgMessageModelDTO> findOne(Long id);

    /**
     * Delete the "id" pgMessageModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
