package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.AccountStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountStatus} and its DTO {@link AccountStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountStatusMapper extends EntityMapper<AccountStatusDTO, AccountStatus> {



    default AccountStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setId(id);
        return accountStatus;
    }
}
