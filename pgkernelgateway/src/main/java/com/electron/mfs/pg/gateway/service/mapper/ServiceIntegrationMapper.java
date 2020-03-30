package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.ServiceIntegrationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceIntegration} and its DTO {@link ServiceIntegrationDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartnerMapper.class})
public interface ServiceIntegrationMapper extends EntityMapper<ServiceIntegrationDTO, ServiceIntegration> {

    @Mapping(source = "partner.id", target = "partnerId")
    ServiceIntegrationDTO toDto(ServiceIntegration serviceIntegration);

    @Mapping(source = "partnerId", target = "partner")
    ServiceIntegration toEntity(ServiceIntegrationDTO serviceIntegrationDTO);

    default ServiceIntegration fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceIntegration serviceIntegration = new ServiceIntegration();
        serviceIntegration.setId(id);
        return serviceIntegration;
    }
}
