package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.BeneficiaryRelationshipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BeneficiaryRelationship} and its DTO {@link BeneficiaryRelationshipDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BeneficiaryRelationshipMapper extends EntityMapper<BeneficiaryRelationshipDTO, BeneficiaryRelationship> {



    default BeneficiaryRelationship fromId(Long id) {
        if (id == null) {
            return null;
        }
        BeneficiaryRelationship beneficiaryRelationship = new BeneficiaryRelationship();
        beneficiaryRelationship.setId(id);
        return beneficiaryRelationship;
    }
}
