package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.ContractOppositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContractOpposition} and its DTO {@link ContractOppositionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContractMapper.class})
public interface ContractOppositionMapper extends EntityMapper<ContractOppositionDTO, ContractOpposition> {

    @Mapping(source = "contract.id", target = "contractId")
    ContractOppositionDTO toDto(ContractOpposition contractOpposition);

    @Mapping(source = "contractId", target = "contract")
    ContractOpposition toEntity(ContractOppositionDTO contractOppositionDTO);

    default ContractOpposition fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContractOpposition contractOpposition = new ContractOpposition();
        contractOpposition.setId(id);
        return contractOpposition;
    }
}
