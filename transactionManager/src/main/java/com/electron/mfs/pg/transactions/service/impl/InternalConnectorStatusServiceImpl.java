package com.electron.mfs.pg.transactions.service.impl;

import com.electron.mfs.pg.transactions.service.InternalConnectorStatusService;
import com.electron.mfs.pg.transactions.domain.InternalConnectorStatus;
import com.electron.mfs.pg.transactions.repository.InternalConnectorStatusRepository;
import com.electron.mfs.pg.transactions.service.dto.InternalConnectorStatusDTO;
import com.electron.mfs.pg.transactions.service.mapper.InternalConnectorStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InternalConnectorStatus}.
 */
@Service
@Transactional
public class InternalConnectorStatusServiceImpl implements InternalConnectorStatusService {

    private final Logger log = LoggerFactory.getLogger(InternalConnectorStatusServiceImpl.class);

    private final InternalConnectorStatusRepository internalConnectorStatusRepository;

    private final InternalConnectorStatusMapper internalConnectorStatusMapper;

    public InternalConnectorStatusServiceImpl(InternalConnectorStatusRepository internalConnectorStatusRepository, InternalConnectorStatusMapper internalConnectorStatusMapper) {
        this.internalConnectorStatusRepository = internalConnectorStatusRepository;
        this.internalConnectorStatusMapper = internalConnectorStatusMapper;
    }

    /**
     * Save a internalConnectorStatus.
     *
     * @param internalConnectorStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InternalConnectorStatusDTO save(InternalConnectorStatusDTO internalConnectorStatusDTO) {
        log.debug("Request to save InternalConnectorStatus : {}", internalConnectorStatusDTO);
        InternalConnectorStatus internalConnectorStatus = internalConnectorStatusMapper.toEntity(internalConnectorStatusDTO);
        internalConnectorStatus = internalConnectorStatusRepository.save(internalConnectorStatus);
        return internalConnectorStatusMapper.toDto(internalConnectorStatus);
    }

    /**
     * Get all the internalConnectorStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InternalConnectorStatusDTO> findAll() {
        log.debug("Request to get all InternalConnectorStatuses");
        return internalConnectorStatusRepository.findAll().stream()
            .map(internalConnectorStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one internalConnectorStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InternalConnectorStatusDTO> findOne(Long id) {
        log.debug("Request to get InternalConnectorStatus : {}", id);
        return internalConnectorStatusRepository.findById(id)
            .map(internalConnectorStatusMapper::toDto);
    }

    /**
     * Delete the internalConnectorStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InternalConnectorStatus : {}", id);
        internalConnectorStatusRepository.deleteById(id);
    }
}
