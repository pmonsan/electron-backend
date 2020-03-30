package com.electron.mfs.pg.account.service.mapper;

import com.electron.mfs.pg.account.domain.*;
import com.electron.mfs.pg.account.service.dto.DetailContractDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetailContract} and its DTO {@link DetailContractDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContractMapper.class})
public interface DetailContractMapper extends EntityMapper<DetailContractDTO, DetailContract> {

    @Mapping(source = "contract.id", target = "contractId")
    DetailContractDTO toDto(DetailContract detailContract);

    @Mapping(source = "contractId", target = "contract")
    DetailContract toEntity(DetailContractDTO detailContractDTO);

    default DetailContract fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailContract detailContract = new DetailContract();
        detailContract.setId(id);
        return detailContract;
    }
}
