package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PgApplicationServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgApplicationService} and its DTO {@link PgApplicationServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgApplicationMapper.class})
public interface PgApplicationServiceMapper extends EntityMapper<PgApplicationServiceDTO, PgApplicationService> {

    @Mapping(source = "pgApplication.id", target = "pgApplicationId")
    PgApplicationServiceDTO toDto(PgApplicationService pgApplicationService);

    @Mapping(source = "pgApplicationId", target = "pgApplication")
    PgApplicationService toEntity(PgApplicationServiceDTO pgApplicationServiceDTO);

    default PgApplicationService fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgApplicationService pgApplicationService = new PgApplicationService();
        pgApplicationService.setId(id);
        return pgApplicationService;
    }
}
