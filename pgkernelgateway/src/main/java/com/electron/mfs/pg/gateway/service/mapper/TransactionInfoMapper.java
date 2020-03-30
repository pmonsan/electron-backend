package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.TransactionInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionInfo} and its DTO {@link TransactionInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface TransactionInfoMapper extends EntityMapper<TransactionInfoDTO, TransactionInfo> {

    @Mapping(source = "transaction.id", target = "transactionId")
    TransactionInfoDTO toDto(TransactionInfo transactionInfo);

    @Mapping(source = "transactionId", target = "transaction")
    TransactionInfo toEntity(TransactionInfoDTO transactionInfoDTO);

    default TransactionInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setId(id);
        return transactionInfo;
    }
}
