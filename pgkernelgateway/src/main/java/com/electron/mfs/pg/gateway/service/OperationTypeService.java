package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.OperationTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.OperationType}.
 */
public interface OperationTypeService {

    /**
     * Save a operationType.
     *
     * @param operationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    OperationTypeDTO save(OperationTypeDTO operationTypeDTO);

    /**
     * Get all the operationTypes.
     *
     * @return the list of entities.
     */
    List<OperationTypeDTO> findAll();


    /**
     * Get the "id" operationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperationTypeDTO> findOne(Long id);

    /**
     * Delete the "id" operationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
