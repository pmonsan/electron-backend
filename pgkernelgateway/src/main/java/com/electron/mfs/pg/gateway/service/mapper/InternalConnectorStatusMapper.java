package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.InternalConnectorStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InternalConnectorStatus} and its DTO {@link InternalConnectorStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InternalConnectorStatusMapper extends EntityMapper<InternalConnectorStatusDTO, InternalConnectorStatus> {



    default InternalConnectorStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        InternalConnectorStatus internalConnectorStatus = new InternalConnectorStatus();
        internalConnectorStatus.setId(id);
        return internalConnectorStatus;
    }
}
