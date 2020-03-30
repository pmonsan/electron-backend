package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.TransactionTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionType} and its DTO {@link TransactionTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionTypeMapper extends EntityMapper<TransactionTypeDTO, TransactionType> {



    default TransactionType fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionType transactionType = new TransactionType();
        transactionType.setId(id);
        return transactionType;
    }
}
