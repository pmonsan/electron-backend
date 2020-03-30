package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.OperationTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OperationType} and its DTO {@link OperationTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OperationTypeMapper extends EntityMapper<OperationTypeDTO, OperationType> {



    default OperationType fromId(Long id) {
        if (id == null) {
            return null;
        }
        OperationType operationType = new OperationType();
        operationType.setId(id);
        return operationType;
    }
}
