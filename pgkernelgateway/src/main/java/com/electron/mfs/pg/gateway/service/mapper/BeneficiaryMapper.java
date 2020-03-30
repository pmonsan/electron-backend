package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.BeneficiaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Beneficiary} and its DTO {@link BeneficiaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface BeneficiaryMapper extends EntityMapper<BeneficiaryDTO, Beneficiary> {

    @Mapping(source = "customer.id", target = "customerId")
    BeneficiaryDTO toDto(Beneficiary beneficiary);

    @Mapping(source = "customerId", target = "customer")
    Beneficiary toEntity(BeneficiaryDTO beneficiaryDTO);

    default Beneficiary fromId(Long id) {
        if (id == null) {
            return null;
        }
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setId(id);
        return beneficiary;
    }
}
