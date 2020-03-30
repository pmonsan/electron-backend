package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.Pg8583StatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pg8583Status} and its DTO {@link Pg8583StatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Pg8583StatusMapper extends EntityMapper<Pg8583StatusDTO, Pg8583Status> {



    default Pg8583Status fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pg8583Status pg8583Status = new Pg8583Status();
        pg8583Status.setId(id);
        return pg8583Status;
    }
}
