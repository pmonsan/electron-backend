package com.electron.mfs.pg.services.service.mapper;

import com.electron.mfs.pg.services.domain.*;
import com.electron.mfs.pg.services.service.dto.PgServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgService} and its DTO {@link PgServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConnectorMapper.class})
public interface PgServiceMapper extends EntityMapper<PgServiceDTO, PgService> {

    @Mapping(source = "sourceConnector.id", target = "sourceConnectorId")
    @Mapping(source = "destinationConnector.id", target = "destinationConnectorId")
    PgServiceDTO toDto(PgService pgService);

    @Mapping(source = "sourceConnectorId", target = "sourceConnector")
    @Mapping(source = "destinationConnectorId", target = "destinationConnector")
    PgService toEntity(PgServiceDTO pgServiceDTO);

    default PgService fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgService pgService = new PgService();
        pgService.setId(id);
        return pgService;
    }
}
