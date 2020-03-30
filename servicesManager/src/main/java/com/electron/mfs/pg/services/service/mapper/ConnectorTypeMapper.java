package com.electron.mfs.pg.services.service.mapper;

import com.electron.mfs.pg.services.domain.*;
import com.electron.mfs.pg.services.service.dto.ConnectorTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConnectorType} and its DTO {@link ConnectorTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConnectorTypeMapper extends EntityMapper<ConnectorTypeDTO, ConnectorType> {



    default ConnectorType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConnectorType connectorType = new ConnectorType();
        connectorType.setId(id);
        return connectorType;
    }
}
