package com.electron.mfs.pg.subscription.service.mapper;

import com.electron.mfs.pg.subscription.domain.*;
import com.electron.mfs.pg.subscription.service.dto.CustomerSubscriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerSubscription} and its DTO {@link CustomerSubscriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerSubscriptionMapper extends EntityMapper<CustomerSubscriptionDTO, CustomerSubscription> {



    default CustomerSubscription fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerSubscription customerSubscription = new CustomerSubscription();
        customerSubscription.setId(id);
        return customerSubscription;
    }
}
