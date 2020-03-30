package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.OperationTypeService;
import com.electron.mfs.pg.mdm.domain.OperationType;
import com.electron.mfs.pg.mdm.repository.OperationTypeRepository;
import com.electron.mfs.pg.mdm.service.dto.OperationTypeDTO;
import com.electron.mfs.pg.mdm.service.mapper.OperationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OperationType}.
 */
@Service
@Transactional
public class OperationTypeServiceImpl implements OperationTypeService {

    private final Logger log = LoggerFactory.getLogger(OperationTypeServiceImpl.class);

    private final OperationTypeRepository operationTypeRepository;

    private final OperationTypeMapper operationTypeMapper;

    public OperationTypeServiceImpl(OperationTypeRepository operationTypeRepository, OperationTypeMapper operationTypeMapper) {
        this.operationTypeRepository = operationTypeRepository;
        this.operationTypeMapper = operationTypeMapper;
    }

    /**
     * Save a operationType.
     *
     * @param operationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OperationTypeDTO save(OperationTypeDTO operationTypeDTO) {
        log.debug("Request to save OperationType : {}", operationTypeDTO);
        OperationType operationType = operationTypeMapper.toEntity(operationTypeDTO);
        operationType = operationTypeRepository.save(operationType);
        return operationTypeMapper.toDto(operationType);
    }

    /**
     * Get all the operationTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OperationTypeDTO> findAll() {
        log.debug("Request to get all OperationTypes");
        return operationTypeRepository.findAll().stream()
            .map(operationTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one operationType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OperationTypeDTO> findOne(Long id) {
        log.debug("Request to get OperationType : {}", id);
        return operationTypeRepository.findById(id)
            .map(operationTypeMapper::toDto);
    }

    /**
     * Delete the operationType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OperationType : {}", id);
        operationTypeRepository.deleteById(id);
    }
}
