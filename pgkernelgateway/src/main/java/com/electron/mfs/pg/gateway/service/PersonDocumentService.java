package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PersonDocumentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PersonDocument}.
 */
public interface PersonDocumentService {

    /**
     * Save a personDocument.
     *
     * @param personDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    PersonDocumentDTO save(PersonDocumentDTO personDocumentDTO);

    /**
     * Get all the personDocuments.
     *
     * @return the list of entities.
     */
    List<PersonDocumentDTO> findAll();


    /**
     * Get the "id" personDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" personDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
