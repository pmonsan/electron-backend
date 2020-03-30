package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PartnerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Partner} and its DTO {@link PartnerDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartnerSecurityMapper.class})
public interface PartnerMapper extends EntityMapper<PartnerDTO, Partner> {

    @Mapping(source = "partnerSecurity.id", target = "partnerSecurityId")
    PartnerDTO toDto(Partner partner);

    @Mapping(source = "partnerSecurityId", target = "partnerSecurity")
    Partner toEntity(PartnerDTO partnerDTO);

    default Partner fromId(Long id) {
        if (id == null) {
            return null;
        }
        Partner partner = new Partner();
        partner.setId(id);
        return partner;
    }
}
