package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.InternalConnectorRequestDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.InternalConnectorRequest}.
 */
public interface InternalConnectorRequestService {

    /**
     * Save a internalConnectorRequest.
     *
     * @param internalConnectorRequestDTO the entity to save.
     * @return the persisted entity.
     */
    InternalConnectorRequestDTO save(InternalConnectorRequestDTO internalConnectorRequestDTO);

    /**
     * Get all the internalConnectorRequests.
     *
     * @return the list of entities.
     */
    List<InternalConnectorRequestDTO> findAll();


    /**
     * Get the "id" internalConnectorRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InternalConnectorRequestDTO> findOne(Long id);

    /**
     * Delete the "id" internalConnectorRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
