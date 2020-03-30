package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgBatchService;
import com.electron.mfs.pg.gateway.domain.PgBatch;
import com.electron.mfs.pg.gateway.repository.PgBatchRepository;
import com.electron.mfs.pg.gateway.service.dto.PgBatchDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgBatchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgBatch}.
 */
@Service
@Transactional
public class PgBatchServiceImpl implements PgBatchService {

    private final Logger log = LoggerFactory.getLogger(PgBatchServiceImpl.class);

    private final PgBatchRepository pgBatchRepository;

    private final PgBatchMapper pgBatchMapper;

    public PgBatchServiceImpl(PgBatchRepository pgBatchRepository, PgBatchMapper pgBatchMapper) {
        this.pgBatchRepository = pgBatchRepository;
        this.pgBatchMapper = pgBatchMapper;
    }

    /**
     * Save a pgBatch.
     *
     * @param pgBatchDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgBatchDTO save(PgBatchDTO pgBatchDTO) {
        log.debug("Request to save PgBatch : {}", pgBatchDTO);
        PgBatch pgBatch = pgBatchMapper.toEntity(pgBatchDTO);
        pgBatch = pgBatchRepository.save(pgBatch);
        return pgBatchMapper.toDto(pgBatch);
    }

    /**
     * Get all the pgBatches.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgBatchDTO> findAll() {
        log.debug("Request to get all PgBatches");
        return pgBatchRepository.findAll().stream()
            .map(pgBatchMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgBatch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgBatchDTO> findOne(Long id) {
        log.debug("Request to get PgBatch : {}", id);
        return pgBatchRepository.findById(id)
            .map(pgBatchMapper::toDto);
    }

    /**
     * Delete the pgBatch by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgBatch : {}", id);
        pgBatchRepository.deleteById(id);
    }
}
