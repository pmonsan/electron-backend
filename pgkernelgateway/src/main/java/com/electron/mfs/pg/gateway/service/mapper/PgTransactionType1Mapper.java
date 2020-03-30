package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PgTransactionType1DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgTransactionType1} and its DTO {@link PgTransactionType1DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgTransactionType1Mapper extends EntityMapper<PgTransactionType1DTO, PgTransactionType1> {



    default PgTransactionType1 fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgTransactionType1 pgTransactionType1 = new PgTransactionType1();
        pgTransactionType1.setId(id);
        return pgTransactionType1;
    }
}
