package com.electron.mfs.pg.services.service.mapper;

import com.electron.mfs.pg.services.domain.*;
import com.electron.mfs.pg.services.service.dto.ConnectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Connector} and its DTO {@link ConnectorDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConnectorTypeMapper.class, PgModuleMapper.class})
public interface ConnectorMapper extends EntityMapper<ConnectorDTO, Connector> {

    @Mapping(source = "connectorType.id", target = "connectorTypeId")
    @Mapping(source = "pgModule.id", target = "pgModuleId")
    ConnectorDTO toDto(Connector connector);

    @Mapping(source = "connectorTypeId", target = "connectorType")
    @Mapping(source = "pgModuleId", target = "pgModule")
    Connector toEntity(ConnectorDTO connectorDTO);

    default Connector fromId(Long id) {
        if (id == null) {
            return null;
        }
        Connector connector = new Connector();
        connector.setId(id);
        return connector;
    }
}
