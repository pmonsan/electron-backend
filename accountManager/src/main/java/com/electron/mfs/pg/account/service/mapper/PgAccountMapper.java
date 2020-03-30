package com.electron.mfs.pg.account.service.mapper;

import com.electron.mfs.pg.account.domain.*;
import com.electron.mfs.pg.account.service.dto.PgAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PgAccount} and its DTO {@link PgAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PgAccountMapper extends EntityMapper<PgAccountDTO, PgAccount> {



    default PgAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        PgAccount pgAccount = new PgAccount();
        pgAccount.setId(id);
        return pgAccount;
    }
}
