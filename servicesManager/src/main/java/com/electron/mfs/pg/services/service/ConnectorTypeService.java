package com.electron.mfs.pg.services.service;

import com.electron.mfs.pg.services.service.dto.ConnectorTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.services.domain.ConnectorType}.
 */
public interface ConnectorTypeService {

    /**
     * Save a connectorType.
     *
     * @param connectorTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ConnectorTypeDTO save(ConnectorTypeDTO connectorTypeDTO);

    /**
     * Get all the connectorTypes.
     *
     * @return the list of entities.
     */
    List<ConnectorTypeDTO> findAll();


    /**
     * Get the "id" connectorType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConnectorTypeDTO> findOne(Long id);

    /**
     * Delete the "id" connectorType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
