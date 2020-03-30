package com.electron.mfs.pg.gateway.service.mapper;

import com.electron.mfs.pg.gateway.domain.*;
import com.electron.mfs.pg.gateway.service.dto.PersonDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonDocument} and its DTO {@link PersonDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface PersonDocumentMapper extends EntityMapper<PersonDocumentDTO, PersonDocument> {

    @Mapping(source = "customer.id", target = "customerId")
    PersonDocumentDTO toDto(PersonDocument personDocument);

    @Mapping(source = "customerId", target = "customer")
    PersonDocument toEntity(PersonDocumentDTO personDocumentDTO);

    default PersonDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonDocument personDocument = new PersonDocument();
        personDocument.setId(id);
        return personDocument;
    }
}
