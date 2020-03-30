package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PersonTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PersonType}.
 */
public interface PersonTypeService {

    /**
     * Save a personType.
     *
     * @param personTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PersonTypeDTO save(PersonTypeDTO personTypeDTO);

    /**
     * Get all the personTypes.
     *
     * @return the list of entities.
     */
    List<PersonTypeDTO> findAll();


    /**
     * Get the "id" personType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonTypeDTO> findOne(Long id);

    /**
     * Delete the "id" personType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
