package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgTransactionType2Service;
import com.electron.mfs.pg.gateway.domain.PgTransactionType2;
import com.electron.mfs.pg.gateway.repository.PgTransactionType2Repository;
import com.electron.mfs.pg.gateway.service.dto.PgTransactionType2DTO;
import com.electron.mfs.pg.gateway.service.mapper.PgTransactionType2Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgTransactionType2}.
 */
@Service
@Transactional
public class PgTransactionType2ServiceImpl implements PgTransactionType2Service {

    private final Logger log = LoggerFactory.getLogger(PgTransactionType2ServiceImpl.class);

    private final PgTransactionType2Repository pgTransactionType2Repository;

    private final PgTransactionType2Mapper pgTransactionType2Mapper;

    public PgTransactionType2ServiceImpl(PgTransactionType2Repository pgTransactionType2Repository, PgTransactionType2Mapper pgTransactionType2Mapper) {
        this.pgTransactionType2Repository = pgTransactionType2Repository;
        this.pgTransactionType2Mapper = pgTransactionType2Mapper;
    }

    /**
     * Save a pgTransactionType2.
     *
     * @param pgTransactionType2DTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgTransactionType2DTO save(PgTransactionType2DTO pgTransactionType2DTO) {
        log.debug("Request to save PgTransactionType2 : {}", pgTransactionType2DTO);
        PgTransactionType2 pgTransactionType2 = pgTransactionType2Mapper.toEntity(pgTransactionType2DTO);
        pgTransactionType2 = pgTransactionType2Repository.save(pgTransactionType2);
        return pgTransactionType2Mapper.toDto(pgTransactionType2);
    }

    /**
     * Get all the pgTransactionType2S.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgTransactionType2DTO> findAll() {
        log.debug("Request to get all PgTransactionType2S");
        return pgTransactionType2Repository.findAll().stream()
            .map(pgTransactionType2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgTransactionType2 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgTransactionType2DTO> findOne(Long id) {
        log.debug("Request to get PgTransactionType2 : {}", id);
        return pgTransactionType2Repository.findById(id)
            .map(pgTransactionType2Mapper::toDto);
    }

    /**
     * Delete the pgTransactionType2 by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgTransactionType2 : {}", id);
        pgTransactionType2Repository.deleteById(id);
    }
}
