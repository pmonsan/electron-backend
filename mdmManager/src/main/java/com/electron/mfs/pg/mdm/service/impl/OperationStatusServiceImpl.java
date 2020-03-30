package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.OperationStatusService;
import com.electron.mfs.pg.mdm.domain.OperationStatus;
import com.electron.mfs.pg.mdm.repository.OperationStatusRepository;
import com.electron.mfs.pg.mdm.service.dto.OperationStatusDTO;
import com.electron.mfs.pg.mdm.service.mapper.OperationStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OperationStatus}.
 */
@Service
@Transactional
public class OperationStatusServiceImpl implements OperationStatusService {

    private final Logger log = LoggerFactory.getLogger(OperationStatusServiceImpl.class);

    private final OperationStatusRepository operationStatusRepository;

    private final OperationStatusMapper operationStatusMapper;

    public OperationStatusServiceImpl(OperationStatusRepository operationStatusRepository, OperationStatusMapper operationStatusMapper) {
        this.operationStatusRepository = operationStatusRepository;
        this.operationStatusMapper = operationStatusMapper;
    }

    /**
     * Save a operationStatus.
     *
     * @param operationStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OperationStatusDTO save(OperationStatusDTO operationStatusDTO) {
        log.debug("Request to save OperationStatus : {}", operationStatusDTO);
        OperationStatus operationStatus = operationStatusMapper.toEntity(operationStatusDTO);
        operationStatus = operationStatusRepository.save(operationStatus);
        return operationStatusMapper.toDto(operationStatus);
    }

    /**
     * Get all the operationStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OperationStatusDTO> findAll() {
        log.debug("Request to get all OperationStatuses");
        return operationStatusRepository.findAll().stream()
            .map(operationStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one operationStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OperationStatusDTO> findOne(Long id) {
        log.debug("Request to get OperationStatus : {}", id);
        return operationStatusRepository.findById(id)
            .map(operationStatusMapper::toDto);
    }

    /**
     * Delete the operationStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OperationStatus : {}", id);
        operationStatusRepository.deleteById(id);
    }
}
