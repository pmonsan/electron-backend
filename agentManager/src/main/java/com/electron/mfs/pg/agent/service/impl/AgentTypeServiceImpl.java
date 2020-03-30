package com.electron.mfs.pg.agent.service.impl;

import com.electron.mfs.pg.agent.service.AgentTypeService;
import com.electron.mfs.pg.agent.domain.AgentType;
import com.electron.mfs.pg.agent.repository.AgentTypeRepository;
import com.electron.mfs.pg.agent.service.dto.AgentTypeDTO;
import com.electron.mfs.pg.agent.service.mapper.AgentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AgentType}.
 */
@Service
@Transactional
public class AgentTypeServiceImpl implements AgentTypeService {

    private final Logger log = LoggerFactory.getLogger(AgentTypeServiceImpl.class);

    private final AgentTypeRepository agentTypeRepository;

    private final AgentTypeMapper agentTypeMapper;

    public AgentTypeServiceImpl(AgentTypeRepository agentTypeRepository, AgentTypeMapper agentTypeMapper) {
        this.agentTypeRepository = agentTypeRepository;
        this.agentTypeMapper = agentTypeMapper;
    }

    /**
     * Save a agentType.
     *
     * @param agentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AgentTypeDTO save(AgentTypeDTO agentTypeDTO) {
        log.debug("Request to save AgentType : {}", agentTypeDTO);
        AgentType agentType = agentTypeMapper.toEntity(agentTypeDTO);
        agentType = agentTypeRepository.save(agentType);
        return agentTypeMapper.toDto(agentType);
    }

    /**
     * Get all the agentTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AgentTypeDTO> findAll() {
        log.debug("Request to get all AgentTypes");
        return agentTypeRepository.findAll().stream()
            .map(agentTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one agentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AgentTypeDTO> findOne(Long id) {
        log.debug("Request to get AgentType : {}", id);
        return agentTypeRepository.findById(id)
            .map(agentTypeMapper::toDto);
    }

    /**
     * Delete the agentType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgentType : {}", id);
        agentTypeRepository.deleteById(id);
    }
}
