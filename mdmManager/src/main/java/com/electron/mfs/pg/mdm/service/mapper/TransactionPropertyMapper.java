package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.TransactionPropertyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionProperty} and its DTO {@link TransactionPropertyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionPropertyMapper extends EntityMapper<TransactionPropertyDTO, TransactionProperty> {



    default TransactionProperty fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionProperty transactionProperty = new TransactionProperty();
        transactionProperty.setId(id);
        return transactionProperty;
    }
}
