package com.electron.mfs.pg.agent.service;

import com.electron.mfs.pg.agent.service.dto.AgentTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.agent.domain.AgentType}.
 */
public interface AgentTypeService {

    /**
     * Save a agentType.
     *
     * @param agentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AgentTypeDTO save(AgentTypeDTO agentTypeDTO);

    /**
     * Get all the agentTypes.
     *
     * @return the list of entities.
     */
    List<AgentTypeDTO> findAll();


    /**
     * Get the "id" agentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgentTypeDTO> findOne(Long id);

    /**
     * Delete the "id" agentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
