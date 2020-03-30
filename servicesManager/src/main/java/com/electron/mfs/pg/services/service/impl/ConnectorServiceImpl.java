package com.electron.mfs.pg.services.service.impl;

import com.electron.mfs.pg.services.service.ConnectorService;
import com.electron.mfs.pg.services.domain.Connector;
import com.electron.mfs.pg.services.repository.ConnectorRepository;
import com.electron.mfs.pg.services.service.dto.ConnectorDTO;
import com.electron.mfs.pg.services.service.mapper.ConnectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Connector}.
 */
@Service
@Transactional
public class ConnectorServiceImpl implements ConnectorService {

    private final Logger log = LoggerFactory.getLogger(ConnectorServiceImpl.class);

    private final ConnectorRepository connectorRepository;

    private final ConnectorMapper connectorMapper;

    public ConnectorServiceImpl(ConnectorRepository connectorRepository, ConnectorMapper connectorMapper) {
        this.connectorRepository = connectorRepository;
        this.connectorMapper = connectorMapper;
    }

    /**
     * Save a connector.
     *
     * @param connectorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConnectorDTO save(ConnectorDTO connectorDTO) {
        log.debug("Request to save Connector : {}", connectorDTO);
        Connector connector = connectorMapper.toEntity(connectorDTO);
        connector = connectorRepository.save(connector);
        return connectorMapper.toDto(connector);
    }

    /**
     * Get all the connectors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConnectorDTO> findAll() {
        log.debug("Request to get all Connectors");
        return connectorRepository.findAll().stream()
            .map(connectorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one connector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConnectorDTO> findOne(Long id) {
        log.debug("Request to get Connector : {}", id);
        return connectorRepository.findById(id)
            .map(connectorMapper::toDto);
    }

    /**
     * Delete the connector by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Connector : {}", id);
        connectorRepository.deleteById(id);
    }
}
