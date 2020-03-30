package com.electron.mfs.pg.transactions.service.mapper;

import com.electron.mfs.pg.transactions.domain.*;
import com.electron.mfs.pg.transactions.service.dto.DetailTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetailTransaction} and its DTO {@link DetailTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface DetailTransactionMapper extends EntityMapper<DetailTransactionDTO, DetailTransaction> {

    @Mapping(source = "transaction.id", target = "transactionId")
    DetailTransactionDTO toDto(DetailTransaction detailTransaction);

    @Mapping(source = "transactionId", target = "transaction")
    DetailTransaction toEntity(DetailTransactionDTO detailTransactionDTO);

    default DetailTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailTransaction detailTransaction = new DetailTransaction();
        detailTransaction.setId(id);
        return detailTransaction;
    }
}
