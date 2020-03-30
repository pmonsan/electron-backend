package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.TransactionChannelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionChannel} and its DTO {@link TransactionChannelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionChannelMapper extends EntityMapper<TransactionChannelDTO, TransactionChannel> {



    default TransactionChannel fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionChannel transactionChannel = new TransactionChannel();
        transactionChannel.setId(id);
        return transactionChannel;
    }
}
