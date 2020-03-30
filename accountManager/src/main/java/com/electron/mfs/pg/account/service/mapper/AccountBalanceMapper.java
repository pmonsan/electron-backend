package com.electron.mfs.pg.account.service.mapper;

import com.electron.mfs.pg.account.domain.*;
import com.electron.mfs.pg.account.service.dto.AccountBalanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountBalance} and its DTO {@link AccountBalanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgAccountMapper.class})
public interface AccountBalanceMapper extends EntityMapper<AccountBalanceDTO, AccountBalance> {

    @Mapping(source = "account.id", target = "accountId")
    AccountBalanceDTO toDto(AccountBalance accountBalance);

    @Mapping(source = "accountId", target = "account")
    AccountBalance toEntity(AccountBalanceDTO accountBalanceDTO);

    default AccountBalance fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setId(id);
        return accountBalance;
    }
}
