package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.TransactionStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionStatus} and its DTO {@link TransactionStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionStatusMapper extends EntityMapper<TransactionStatusDTO, TransactionStatus> {



    default TransactionStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionStatus transactionStatus = new TransactionStatus();
        transactionStatus.setId(id);
        return transactionStatus;
    }
}
