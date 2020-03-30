package com.electron.mfs.pg.transactions.service.impl;

import com.electron.mfs.pg.transactions.service.InternalConnectorRequestService;
import com.electron.mfs.pg.transactions.domain.InternalConnectorRequest;
import com.electron.mfs.pg.transactions.repository.InternalConnectorRequestRepository;
import com.electron.mfs.pg.transactions.service.dto.InternalConnectorRequestDTO;
import com.electron.mfs.pg.transactions.service.mapper.InternalConnectorRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InternalConnectorRequest}.
 */
@Service
@Transactional
public class InternalConnectorRequestServiceImpl implements InternalConnectorRequestService {

    private final Logger log = LoggerFactory.getLogger(InternalConnectorRequestServiceImpl.class);

    private final InternalConnectorRequestRepository internalConnectorRequestRepository;

    private final InternalConnectorRequestMapper internalConnectorRequestMapper;

    public InternalConnectorRequestServiceImpl(InternalConnectorRequestRepository internalConnectorRequestRepository, InternalConnectorRequestMapper internalConnectorRequestMapper) {
        this.internalConnectorRequestRepository = internalConnectorRequestRepository;
        this.internalConnectorRequestMapper = internalConnectorRequestMapper;
    }

    /**
     * Save a internalConnectorRequest.
     *
     * @param internalConnectorRequestDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InternalConnectorRequestDTO save(InternalConnectorRequestDTO internalConnectorRequestDTO) {
        log.debug("Request to save InternalConnectorRequest : {}", internalConnectorRequestDTO);
        InternalConnectorRequest internalConnectorRequest = internalConnectorRequestMapper.toEntity(internalConnectorRequestDTO);
        internalConnectorRequest = internalConnectorRequestRepository.save(internalConnectorRequest);
        return internalConnectorRequestMapper.toDto(internalConnectorRequest);
    }

    /**
     * Get all the internalConnectorRequests.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InternalConnectorRequestDTO> findAll() {
        log.debug("Request to get all InternalConnectorRequests");
        return internalConnectorRequestRepository.findAll().stream()
            .map(internalConnectorRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one internalConnectorRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InternalConnectorRequestDTO> findOne(Long id) {
        log.debug("Request to get InternalConnectorRequest : {}", id);
        return internalConnectorRequestRepository.findById(id)
            .map(internalConnectorRequestMapper::toDto);
    }

    /**
     * Delete the internalConnectorRequest by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InternalConnectorRequest : {}", id);
        internalConnectorRequestRepository.deleteById(id);
    }
}
