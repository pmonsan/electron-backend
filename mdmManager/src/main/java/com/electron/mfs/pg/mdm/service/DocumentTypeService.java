package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.DocumentTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.DocumentType}.
 */
public interface DocumentTypeService {

    /**
     * Save a documentType.
     *
     * @param documentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentTypeDTO save(DocumentTypeDTO documentTypeDTO);

    /**
     * Get all the documentTypes.
     *
     * @return the list of entities.
     */
    List<DocumentTypeDTO> findAll();


    /**
     * Get the "id" documentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentTypeDTO> findOne(Long id);

    /**
     * Delete the "id" documentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
