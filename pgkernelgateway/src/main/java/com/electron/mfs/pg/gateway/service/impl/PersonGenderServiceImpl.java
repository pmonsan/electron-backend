package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PersonGenderService;
import com.electron.mfs.pg.gateway.domain.PersonGender;
import com.electron.mfs.pg.gateway.repository.PersonGenderRepository;
import com.electron.mfs.pg.gateway.service.dto.PersonGenderDTO;
import com.electron.mfs.pg.gateway.service.mapper.PersonGenderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PersonGender}.
 */
@Service
@Transactional
public class PersonGenderServiceImpl implements PersonGenderService {

    private final Logger log = LoggerFactory.getLogger(PersonGenderServiceImpl.class);

    private final PersonGenderRepository personGenderRepository;

    private final PersonGenderMapper personGenderMapper;

    public PersonGenderServiceImpl(PersonGenderRepository personGenderRepository, PersonGenderMapper personGenderMapper) {
        this.personGenderRepository = personGenderRepository;
        this.personGenderMapper = personGenderMapper;
    }

    /**
     * Save a personGender.
     *
     * @param personGenderDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonGenderDTO save(PersonGenderDTO personGenderDTO) {
        log.debug("Request to save PersonGender : {}", personGenderDTO);
        PersonGender personGender = personGenderMapper.toEntity(personGenderDTO);
        personGender = personGenderRepository.save(personGender);
        return personGenderMapper.toDto(personGender);
    }

    /**
     * Get all the personGenders.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonGenderDTO> findAll() {
        log.debug("Request to get all PersonGenders");
        return personGenderRepository.findAll().stream()
            .map(personGenderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personGender by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonGenderDTO> findOne(Long id) {
        log.debug("Request to get PersonGender : {}", id);
        return personGenderRepository.findById(id)
            .map(personGenderMapper::toDto);
    }

    /**
     * Delete the personGender by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonGender : {}", id);
        personGenderRepository.deleteById(id);
    }
}
