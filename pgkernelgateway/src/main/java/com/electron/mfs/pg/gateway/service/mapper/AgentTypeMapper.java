package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.AgentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AgentType} and its DTO {@link AgentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgentTypeMapper extends EntityMapper<AgentTypeDTO, AgentType> {



    default AgentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AgentType agentType = new AgentType();
        agentType.setId(id);
        return agentType;
    }
}
