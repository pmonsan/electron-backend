package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.Pg8583CallbackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pg8583Callback} and its DTO {@link Pg8583CallbackDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Pg8583CallbackMapper extends EntityMapper<Pg8583CallbackDTO, Pg8583Callback> {



    default Pg8583Callback fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pg8583Callback pg8583Callback = new Pg8583Callback();
        pg8583Callback.setId(id);
        return pg8583Callback;
    }
}
