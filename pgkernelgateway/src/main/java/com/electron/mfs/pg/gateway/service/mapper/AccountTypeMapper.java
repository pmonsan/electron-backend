package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.AccountTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountType} and its DTO {@link AccountTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountTypeMapper extends EntityMapper<AccountTypeDTO, AccountType> {



    default AccountType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountType accountType = new AccountType();
        accountType.setId(id);
        return accountType;
    }
}
