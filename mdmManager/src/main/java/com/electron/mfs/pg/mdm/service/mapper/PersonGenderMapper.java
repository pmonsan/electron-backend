package com.electron.mfs.pg.mdm.service.mapper;

import com.electron.mfs.pg.mdm.domain.*;
import com.electron.mfs.pg.mdm.service.dto.PersonGenderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonGender} and its DTO {@link PersonGenderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonGenderMapper extends EntityMapper<PersonGenderDTO, PersonGender> {



    default PersonGender fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonGender personGender = new PersonGender();
        personGender.setId(id);
        return personGender;
    }
}
