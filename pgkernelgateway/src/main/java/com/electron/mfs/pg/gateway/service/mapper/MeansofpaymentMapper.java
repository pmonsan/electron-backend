package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Meansofpayment} and its DTO {@link MeansofpaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface MeansofpaymentMapper extends EntityMapper<MeansofpaymentDTO, Meansofpayment> {

    @Mapping(source = "customer.id", target = "customerId")
    MeansofpaymentDTO toDto(Meansofpayment meansofpayment);

    @Mapping(source = "customerId", target = "customer")
    Meansofpayment toEntity(MeansofpaymentDTO meansofpaymentDTO);

    default Meansofpayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Meansofpayment meansofpayment = new Meansofpayment();
        meansofpayment.setId(id);
        return meansofpayment;
    }
}
