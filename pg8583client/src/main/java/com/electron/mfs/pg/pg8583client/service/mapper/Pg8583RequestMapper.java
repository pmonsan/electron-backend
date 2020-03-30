package com.electron.mfs.pg.pg8583client.service.mapper;

import com.electron.mfs.pg.pg8583client.domain.*;
import com.electron.mfs.pg.pg8583client.service.dto.Pg8583RequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pg8583Request} and its DTO {@link Pg8583RequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Pg8583RequestMapper extends EntityMapper<Pg8583RequestDTO, Pg8583Request> {



    default Pg8583Request fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pg8583Request pg8583Request = new Pg8583Request();
        pg8583Request.setId(id);
        return pg8583Request;
    }
}
