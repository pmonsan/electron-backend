package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.CustomerLimitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerLimit} and its DTO {@link CustomerLimitDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface CustomerLimitMapper extends EntityMapper<CustomerLimitDTO, CustomerLimit> {

    @Mapping(source = "customer.id", target = "customerId")
    CustomerLimitDTO toDto(CustomerLimit customerLimit);

    @Mapping(source = "customerId", target = "customer")
    CustomerLimit toEntity(CustomerLimitDTO customerLimitDTO);

    default CustomerLimit fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerLimit customerLimit = new CustomerLimit();
        customerLimit.setId(id);
        return customerLimit;
    }
}
