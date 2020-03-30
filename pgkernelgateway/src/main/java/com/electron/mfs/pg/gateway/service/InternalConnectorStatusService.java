package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.InternalConnectorStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.InternalConnectorStatus}.
 */
public interface InternalConnectorStatusService {

    /**
     * Save a internalConnectorStatus.
     *
     * @param internalConnectorStatusDTO the entity to save.
     * @return the persisted entity.
     */
    InternalConnectorStatusDTO save(InternalConnectorStatusDTO internalConnectorStatusDTO);

    /**
     * Get all the internalConnectorStatuses.
     *
     * @return the list of entities.
     */
    List<InternalConnectorStatusDTO> findAll();


    /**
     * Get the "id" internalConnectorStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InternalConnectorStatusDTO> findOne(Long id);

    /**
     * Delete the "id" internalConnectorStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
