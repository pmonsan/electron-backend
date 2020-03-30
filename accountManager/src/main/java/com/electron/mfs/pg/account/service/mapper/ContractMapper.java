package com.electron.mfs.pg.account.service.mapper;

import com.electron.mfs.pg.account.domain.*;
import com.electron.mfs.pg.account.service.dto.ContractDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contract} and its DTO {@link ContractDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgAccountMapper.class})
public interface ContractMapper extends EntityMapper<ContractDTO, Contract> {

    @Mapping(source = "account.id", target = "accountId")
    ContractDTO toDto(Contract contract);

    @Mapping(source = "accountId", target = "account")
    Contract toEntity(ContractDTO contractDTO);

    default Contract fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contract contract = new Contract();
        contract.setId(id);
        return contract;
    }
}
