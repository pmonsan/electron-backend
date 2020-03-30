package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgApplicationServiceService;
import com.electron.mfs.pg.gateway.domain.PgApplicationService;
import com.electron.mfs.pg.gateway.repository.PgApplicationServiceRepository;
import com.electron.mfs.pg.gateway.service.dto.PgApplicationServiceDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgApplicationServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgApplicationService}.
 */
@Service
@Transactional
public class PgApplicationServiceServiceImpl implements PgApplicationServiceService {

    private final Logger log = LoggerFactory.getLogger(PgApplicationServiceServiceImpl.class);

    private final PgApplicationServiceRepository pgApplicationServiceRepository;

    private final PgApplicationServiceMapper pgApplicationServiceMapper;

    public PgApplicationServiceServiceImpl(PgApplicationServiceRepository pgApplicationServiceRepository, PgApplicationServiceMapper pgApplicationServiceMapper) {
        this.pgApplicationServiceRepository = pgApplicationServiceRepository;
        this.pgApplicationServiceMapper = pgApplicationServiceMapper;
    }

    /**
     * Save a pgApplicationService.
     *
     * @param pgApplicationServiceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgApplicationServiceDTO save(PgApplicationServiceDTO pgApplicationServiceDTO) {
        log.debug("Request to save PgApplicationService : {}", pgApplicationServiceDTO);
        PgApplicationService pgApplicationService = pgApplicationServiceMapper.toEntity(pgApplicationServiceDTO);
        pgApplicationService = pgApplicationServiceRepository.save(pgApplicationService);
        return pgApplicationServiceMapper.toDto(pgApplicationService);
    }

    /**
     * Get all the pgApplicationServices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgApplicationServiceDTO> findAll() {
        log.debug("Request to get all PgApplicationServices");
        return pgApplicationServiceRepository.findAll().stream()
            .map(pgApplicationServiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgApplicationService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgApplicationServiceDTO> findOne(Long id) {
        log.debug("Request to get PgApplicationService : {}", id);
        return pgApplicationServiceRepository.findById(id)
            .map(pgApplicationServiceMapper::toDto);
    }

    /**
     * Delete the pgApplicationService by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgApplicationService : {}", id);
        pgApplicationServiceRepository.deleteById(id);
    }
}
