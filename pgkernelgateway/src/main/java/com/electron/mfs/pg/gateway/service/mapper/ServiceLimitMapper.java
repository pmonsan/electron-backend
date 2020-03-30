package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.ServiceLimitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceLimit} and its DTO {@link ServiceLimitDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgServiceMapper.class})
public interface ServiceLimitMapper extends EntityMapper<ServiceLimitDTO, ServiceLimit> {

    @Mapping(source = "pgService.id", target = "pgServiceId")
    ServiceLimitDTO toDto(ServiceLimit serviceLimit);

    @Mapping(source = "pgServiceId", target = "pgService")
    ServiceLimit toEntity(ServiceLimitDTO serviceLimitDTO);

    default ServiceLimit fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceLimit serviceLimit = new ServiceLimit();
        serviceLimit.setId(id);
        return serviceLimit;
    }
}
