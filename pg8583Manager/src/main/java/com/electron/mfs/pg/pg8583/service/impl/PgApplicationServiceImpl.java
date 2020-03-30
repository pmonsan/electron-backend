package com.electron.mfs.pg.pg8583.service.impl;

import com.electron.mfs.pg.pg8583.service.PgApplicationService;
import com.electron.mfs.pg.pg8583.domain.PgApplication;
import com.electron.mfs.pg.pg8583.repository.PgApplicationRepository;
import com.electron.mfs.pg.pg8583.service.dto.PgApplicationDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgApplicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgApplication}.
 */
@Service
@Transactional
public class PgApplicationServiceImpl implements PgApplicationService {

    private final Logger log = LoggerFactory.getLogger(PgApplicationServiceImpl.class);

    private final PgApplicationRepository pgApplicationRepository;

    private final PgApplicationMapper pgApplicationMapper;

    public PgApplicationServiceImpl(PgApplicationRepository pgApplicationRepository, PgApplicationMapper pgApplicationMapper) {
        this.pgApplicationRepository = pgApplicationRepository;
        this.pgApplicationMapper = pgApplicationMapper;
    }

    /**
     * Save a pgApplication.
     *
     * @param pgApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgApplicationDTO save(PgApplicationDTO pgApplicationDTO) {
        log.debug("Request to save PgApplication : {}", pgApplicationDTO);
        PgApplication pgApplication = pgApplicationMapper.toEntity(pgApplicationDTO);
        pgApplication = pgApplicationRepository.save(pgApplication);
        return pgApplicationMapper.toDto(pgApplication);
    }

    /**
     * Get all the pgApplications.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgApplicationDTO> findAll() {
        log.debug("Request to get all PgApplications");
        return pgApplicationRepository.findAll().stream()
            .map(pgApplicationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgApplicationDTO> findOne(Long id) {
        log.debug("Request to get PgApplication : {}", id);
        return pgApplicationRepository.findById(id)
            .map(pgApplicationMapper::toDto);
    }

    /**
     * Delete the pgApplication by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgApplication : {}", id);
        pgApplicationRepository.deleteById(id);
    }
}
