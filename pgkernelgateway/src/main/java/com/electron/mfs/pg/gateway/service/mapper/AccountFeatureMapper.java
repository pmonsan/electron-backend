package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.AccountFeatureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountFeature} and its DTO {@link AccountFeatureDTO}.
 */
@Mapper(componentModel = "spring", uses = {PgAccountMapper.class})
public interface AccountFeatureMapper extends EntityMapper<AccountFeatureDTO, AccountFeature> {

    @Mapping(source = "account.id", target = "accountId")
    AccountFeatureDTO toDto(AccountFeature accountFeature);

    @Mapping(source = "accountId", target = "account")
    AccountFeature toEntity(AccountFeatureDTO accountFeatureDTO);

    default AccountFeature fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountFeature accountFeature = new AccountFeature();
        accountFeature.setId(id);
        return accountFeature;
    }
}
