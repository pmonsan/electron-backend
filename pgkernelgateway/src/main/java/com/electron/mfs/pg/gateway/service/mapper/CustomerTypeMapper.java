package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.CustomerTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerType} and its DTO {@link CustomerTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerTypeMapper extends EntityMapper<CustomerTypeDTO, CustomerType> {



    default CustomerType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerType customerType = new CustomerType();
        customerType.setId(id);
        return customerType;
    }
}
