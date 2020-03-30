package com.electron.mfs.pg.transactions.service.mapper;

import com.electron.mfs.pg.transactions.domain.*;
import com.electron.mfs.pg.transactions.service.dto.InternalConnectorStatusDTO;

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
