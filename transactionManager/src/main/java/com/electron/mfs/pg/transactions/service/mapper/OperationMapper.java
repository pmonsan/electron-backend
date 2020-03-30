package com.electron.mfs.pg.transactions.service.mapper;

import com.electron.mfs.pg.transactions.domain.*;
import com.electron.mfs.pg.transactions.service.dto.OperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Operation} and its DTO {@link OperationDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface OperationMapper extends EntityMapper<OperationDTO, Operation> {

    @Mapping(source = "transaction.id", target = "transactionId")
    OperationDTO toDto(Operation operation);

    @Mapping(source = "transactionId", target = "transaction")
    Operation toEntity(OperationDTO operationDTO);

    default Operation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operation operation = new Operation();
        operation.setId(id);
        return operation;
    }
}
