package com.electron.mfs.pg.pg8583.service;

import com.electron.mfs.pg.pg8583.service.dto.PgResponseCodeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.pg8583.domain.PgResponseCode}.
 */
public interface PgResponseCodeService {

    /**
     * Save a pgResponseCode.
     *
     * @param pgResponseCodeDTO the entity to save.
     * @return the persisted entity.
     */
    PgResponseCodeDTO save(PgResponseCodeDTO pgResponseCodeDTO);

    /**
     * Get all the pgResponseCodes.
     *
     * @return the list of entities.
     */
    List<PgResponseCodeDTO> findAll();


    /**
     * Get the "id" pgResponseCode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PgResponseCodeDTO> findOne(Long id);

    /**
     * Delete the "id" pgResponseCode.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
