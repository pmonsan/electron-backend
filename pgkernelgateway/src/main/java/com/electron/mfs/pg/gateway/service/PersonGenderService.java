package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PersonGenderDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PersonGender}.
 */
public interface PersonGenderService {

    /**
     * Save a personGender.
     *
     * @param personGenderDTO the entity to save.
     * @return the persisted entity.
     */
    PersonGenderDTO save(PersonGenderDTO personGenderDTO);

    /**
     * Get all the personGenders.
     *
     * @return the list of entities.
     */
    List<PersonGenderDTO> findAll();


    /**
     * Get the "id" personGender.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonGenderDTO> findOne(Long id);

    /**
     * Delete the "id" personGender.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
