package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.PersonTypeService;
import com.electron.mfs.pg.mdm.domain.PersonType;
import com.electron.mfs.pg.mdm.repository.PersonTypeRepository;
import com.electron.mfs.pg.mdm.service.dto.PersonTypeDTO;
import com.electron.mfs.pg.mdm.service.mapper.PersonTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PersonType}.
 */
@Service
@Transactional
public class PersonTypeServiceImpl implements PersonTypeService {

    private final Logger log = LoggerFactory.getLogger(PersonTypeServiceImpl.class);

    private final PersonTypeRepository personTypeRepository;

    private final PersonTypeMapper personTypeMapper;

    public PersonTypeServiceImpl(PersonTypeRepository personTypeRepository, PersonTypeMapper personTypeMapper) {
        this.personTypeRepository = personTypeRepository;
        this.personTypeMapper = personTypeMapper;
    }

    /**
     * Save a personType.
     *
     * @param personTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonTypeDTO save(PersonTypeDTO personTypeDTO) {
        log.debug("Request to save PersonType : {}", personTypeDTO);
        PersonType personType = personTypeMapper.toEntity(personTypeDTO);
        personType = personTypeRepository.save(personType);
        return personTypeMapper.toDto(personType);
    }

    /**
     * Get all the personTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonTypeDTO> findAll() {
        log.debug("Request to get all PersonTypes");
        return personTypeRepository.findAll().stream()
            .map(personTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonTypeDTO> findOne(Long id) {
        log.debug("Request to get PersonType : {}", id);
        return personTypeRepository.findById(id)
            .map(personTypeMapper::toDto);
    }

    /**
     * Delete the personType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonType : {}", id);
        personTypeRepository.deleteById(id);
    }
}
