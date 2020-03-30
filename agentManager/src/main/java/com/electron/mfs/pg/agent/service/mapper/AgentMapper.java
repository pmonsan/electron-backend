package com.electron.mfs.pg.agent.service.mapper;

import com.electron.mfs.pg.agent.domain.*;
import com.electron.mfs.pg.agent.service.dto.AgentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agent} and its DTO {@link AgentDTO}.
 */
@Mapper(componentModel = "spring", uses = {AgentTypeMapper.class})
public interface AgentMapper extends EntityMapper<AgentDTO, Agent> {

    @Mapping(source = "agentType.id", target = "agentTypeId")
    AgentDTO toDto(Agent agent);

    @Mapping(source = "agentTypeId", target = "agentType")
    Agent toEntity(AgentDTO agentDTO);

    default Agent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agent agent = new Agent();
        agent.setId(id);
        return agent;
    }
}
