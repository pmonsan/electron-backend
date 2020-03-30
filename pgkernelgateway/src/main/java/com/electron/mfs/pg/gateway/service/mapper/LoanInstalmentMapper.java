package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.LoanInstalmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanInstalment} and its DTO {@link LoanInstalmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface LoanInstalmentMapper extends EntityMapper<LoanInstalmentDTO, LoanInstalment> {

    @Mapping(source = "loan.id", target = "loanId")
    LoanInstalmentDTO toDto(LoanInstalment loanInstalment);

    @Mapping(source = "loanId", target = "loan")
    LoanInstalment toEntity(LoanInstalmentDTO loanInstalmentDTO);

    default LoanInstalment fromId(Long id) {
        if (id == null) {
            return null;
        }
        LoanInstalment loanInstalment = new LoanInstalment();
        loanInstalment.setId(id);
        return loanInstalment;
    }
}
