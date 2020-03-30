package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.ServiceChannelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceChannel} and its DTO {@link ServiceChannelDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgServiceMapper.class})
public interface ServiceChannelMapper extends EntityMapper<ServiceChannelDTO, ServiceChannel> {

    @Mapping(source = "pgService.id", target = "pgServiceId")
    ServiceChannelDTO toDto(ServiceChannel serviceChannel);

    @Mapping(source = "pgServiceId", target = "pgService")
    ServiceChannel toEntity(ServiceChannelDTO serviceChannelDTO);

    default ServiceChannel fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceChannel serviceChannel = new ServiceChannel();
        serviceChannel.setId(id);
        return serviceChannel;
    }
}
