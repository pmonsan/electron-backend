package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.CustomerBlacklistDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerBlacklist} and its DTO {@link CustomerBlacklistDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface CustomerBlacklistMapper extends EntityMapper<CustomerBlacklistDTO, CustomerBlacklist> {

    @Mapping(source = "customer.id", target = "customerId")
    CustomerBlacklistDTO toDto(CustomerBlacklist customerBlacklist);

    @Mapping(source = "customerId", target = "customer")
    CustomerBlacklist toEntity(CustomerBlacklistDTO customerBlacklistDTO);

    default CustomerBlacklist fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerBlacklist customerBlacklist = new CustomerBlacklist();
        customerBlacklist.setId(id);
        return customerBlacklist;
    }
}
