package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.TransactionGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionGroup} and its DTO {@link TransactionGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionGroupMapper extends EntityMapper<TransactionGroupDTO, TransactionGroup> {



    default TransactionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionGroup transactionGroup = new TransactionGroup();
        transactionGroup.setId(id);
        return transactionGroup;
    }
}
