package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.OperationStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.OperationStatus}.
 */
public interface OperationStatusService {

    /**
     * Save a operationStatus.
     *
     * @param operationStatusDTO the entity to save.
     * @return the persisted entity.
     */
    OperationStatusDTO save(OperationStatusDTO operationStatusDTO);

    /**
     * Get all the operationStatuses.
     *
     * @return the list of entities.
     */
    List<OperationStatusDTO> findAll();


    /**
     * Get the "id" operationStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperationStatusDTO> findOne(Long id);

    /**
     * Delete the "id" operationStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
