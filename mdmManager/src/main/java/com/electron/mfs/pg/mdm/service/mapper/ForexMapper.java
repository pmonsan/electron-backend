package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.ForexDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Forex} and its DTO {@link ForexDTO}.
 */
@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface ForexMapper extends EntityMapper<ForexDTO, Forex> {

    @Mapping(source = "fromCurrency.id", target = "fromCurrencyId")
    @Mapping(source = "toCurrency.id", target = "toCurrencyId")
    ForexDTO toDto(Forex forex);

    @Mapping(source = "fromCurrencyId", target = "fromCurrency")
    @Mapping(source = "toCurrencyId", target = "toCurrency")
    Forex toEntity(ForexDTO forexDTO);

    default Forex fromId(Long id) {
        if (id == null) {
            return null;
        }
        Forex forex = new Forex();
        forex.setId(id);
        return forex;
    }
}
