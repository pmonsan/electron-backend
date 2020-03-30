package com.electron.mfs.pg.customer.service.impl;

import com.electron.mfs.pg.customer.service.PersonDocumentService;
import com.electron.mfs.pg.customer.domain.PersonDocument;
import com.electron.mfs.pg.customer.repository.PersonDocumentRepository;
import com.electron.mfs.pg.customer.service.dto.PersonDocumentDTO;
import com.electron.mfs.pg.customer.service.mapper.PersonDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PersonDocument}.
 */
@Service
@Transactional
public class PersonDocumentServiceImpl implements PersonDocumentService {

    private final Logger log = LoggerFactory.getLogger(PersonDocumentServiceImpl.class);

    private final PersonDocumentRepository personDocumentRepository;

    private final PersonDocumentMapper personDocumentMapper;

    public PersonDocumentServiceImpl(PersonDocumentRepository personDocumentRepository, PersonDocumentMapper personDocumentMapper) {
        this.personDocumentRepository = personDocumentRepository;
        this.personDocumentMapper = personDocumentMapper;
    }

    /**
     * Save a personDocument.
     *
     * @param personDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonDocumentDTO save(PersonDocumentDTO personDocumentDTO) {
        log.debug("Request to save PersonDocument : {}", personDocumentDTO);
        PersonDocument personDocument = personDocumentMapper.toEntity(personDocumentDTO);
        personDocument = personDocumentRepository.save(personDocument);
        return personDocumentMapper.toDto(personDocument);
    }

    /**
     * Get all the personDocuments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonDocumentDTO> findAll() {
        log.debug("Request to get all PersonDocuments");
        return personDocumentRepository.findAll().stream()
            .map(personDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonDocumentDTO> findOne(Long id) {
        log.debug("Request to get PersonDocument : {}", id);
        return personDocumentRepository.findById(id)
            .map(personDocumentMapper::toDto);
    }

    /**
     * Delete the personDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonDocument : {}", id);
        personDocumentRepository.deleteById(id);
    }
}
