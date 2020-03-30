package com.electron.mfs.pg.pg8583.service.mapper;

import com.electron.mfs.pg.pg8583.domain.*;
import com.electron.mfs.pg.pg8583.service.dto.PgTransactionType2DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgTransactionType2} and its DTO {@link PgTransactionType2DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgTransactionType2Mapper extends EntityMapper<PgTransactionType2DTO, PgTransactionType2> {



    default PgTransactionType2 fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgTransactionType2 pgTransactionType2 = new PgTransactionType2();
        pgTransactionType2.setId(id);
        return pgTransactionType2;
    }
}
