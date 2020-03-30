package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.ConnectorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.Connector}.
 */
public interface ConnectorService {

    /**
     * Save a connector.
     *
     * @param connectorDTO the entity to save.
     * @return the persisted entity.
     */
    ConnectorDTO save(ConnectorDTO connectorDTO);

    /**
     * Get all the connectors.
     *
     * @return the list of entities.
     */
    List<ConnectorDTO> findAll();


    /**
     * Get the "id" connector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConnectorDTO> findOne(Long id);

    /**
     * Delete the "id" connector.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
