package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.TransactionPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionPrice} and its DTO {@link TransactionPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface TransactionPriceMapper extends EntityMapper<TransactionPriceDTO, TransactionPrice> {

    @Mapping(source = "transaction.id", target = "transactionId")
    TransactionPriceDTO toDto(TransactionPrice transactionPrice);

    @Mapping(source = "transactionId", target = "transaction")
    TransactionPrice toEntity(TransactionPriceDTO transactionPriceDTO);

    default TransactionPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionPrice transactionPrice = new TransactionPrice();
        transactionPrice.setId(id);
        return transactionPrice;
    }
}
