package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.LoanInstalmentStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanInstalmentStatus} and its DTO {@link LoanInstalmentStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoanInstalmentStatusMapper extends EntityMapper<LoanInstalmentStatusDTO, LoanInstalmentStatus> {



    default LoanInstalmentStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        LoanInstalmentStatus loanInstalmentStatus = new LoanInstalmentStatus();
        loanInstalmentStatus.setId(id);
        return loanInstalmentStatus;
    }
}
