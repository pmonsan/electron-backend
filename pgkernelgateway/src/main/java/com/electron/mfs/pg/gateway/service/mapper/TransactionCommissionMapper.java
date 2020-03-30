package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.TransactionCommissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionCommission} and its DTO {@link TransactionCommissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface TransactionCommissionMapper extends EntityMapper<TransactionCommissionDTO, TransactionCommission> {

    @Mapping(source = "transaction.id", target = "transactionId")
    TransactionCommissionDTO toDto(TransactionCommission transactionCommission);

    @Mapping(source = "transactionId", target = "transaction")
    TransactionCommission toEntity(TransactionCommissionDTO transactionCommissionDTO);

    default TransactionCommission fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionCommission transactionCommission = new TransactionCommission();
        transactionCommission.setId(id);
        return transactionCommission;
    }
}
