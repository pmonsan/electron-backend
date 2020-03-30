package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgModuleService;
import com.electron.mfs.pg.gateway.domain.PgModule;
import com.electron.mfs.pg.gateway.repository.PgModuleRepository;
import com.electron.mfs.pg.gateway.service.dto.PgModuleDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgModule}.
 */
@Service
@Transactional
public class PgModuleServiceImpl implements PgModuleService {

    private final Logger log = LoggerFactory.getLogger(PgModuleServiceImpl.class);

    private final PgModuleRepository pgModuleRepository;

    private final PgModuleMapper pgModuleMapper;

    public PgModuleServiceImpl(PgModuleRepository pgModuleRepository, PgModuleMapper pgModuleMapper) {
        this.pgModuleRepository = pgModuleRepository;
        this.pgModuleMapper = pgModuleMapper;
    }

    /**
     * Save a pgModule.
     *
     * @param pgModuleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgModuleDTO save(PgModuleDTO pgModuleDTO) {
        log.debug("Request to save PgModule : {}", pgModuleDTO);
        PgModule pgModule = pgModuleMapper.toEntity(pgModuleDTO);
        pgModule = pgModuleRepository.save(pgModule);
        return pgModuleMapper.toDto(pgModule);
    }

    /**
     * Get all the pgModules.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgModuleDTO> findAll() {
        log.debug("Request to get all PgModules");
        return pgModuleRepository.findAll().stream()
            .map(pgModuleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgModule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgModuleDTO> findOne(Long id) {
        log.debug("Request to get PgModule : {}", id);
        return pgModuleRepository.findById(id)
            .map(pgModuleMapper::toDto);
    }

    /**
     * Delete the pgModule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgModule : {}", id);
        pgModuleRepository.deleteById(id);
    }
}
