package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.ConnectorTypeService;
import com.electron.mfs.pg.gateway.domain.ConnectorType;
import com.electron.mfs.pg.gateway.repository.ConnectorTypeRepository;
import com.electron.mfs.pg.gateway.service.dto.ConnectorTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.ConnectorTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ConnectorType}.
 */
@Service
@Transactional
public class ConnectorTypeServiceImpl implements ConnectorTypeService {

    private final Logger log = LoggerFactory.getLogger(ConnectorTypeServiceImpl.class);

    private final ConnectorTypeRepository connectorTypeRepository;

    private final ConnectorTypeMapper connectorTypeMapper;

    public ConnectorTypeServiceImpl(ConnectorTypeRepository connectorTypeRepository, ConnectorTypeMapper connectorTypeMapper) {
        this.connectorTypeRepository = connectorTypeRepository;
        this.connectorTypeMapper = connectorTypeMapper;
    }

    /**
     * Save a connectorType.
     *
     * @param connectorTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConnectorTypeDTO save(ConnectorTypeDTO connectorTypeDTO) {
        log.debug("Request to save ConnectorType : {}", connectorTypeDTO);
        ConnectorType connectorType = connectorTypeMapper.toEntity(connectorTypeDTO);
        connectorType = connectorTypeRepository.save(connectorType);
        return connectorTypeMapper.toDto(connectorType);
    }

    /**
     * Get all the connectorTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConnectorTypeDTO> findAll() {
        log.debug("Request to get all ConnectorTypes");
        return connectorTypeRepository.findAll().stream()
            .map(connectorTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one connectorType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConnectorTypeDTO> findOne(Long id) {
        log.debug("Request to get ConnectorType : {}", id);
        return connectorTypeRepository.findById(id)
            .map(connectorTypeMapper::toDto);
    }

    /**
     * Delete the connectorType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConnectorType : {}", id);
        connectorTypeRepository.deleteById(id);
    }
}
