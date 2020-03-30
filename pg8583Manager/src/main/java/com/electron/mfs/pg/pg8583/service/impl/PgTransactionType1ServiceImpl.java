package com.electron.mfs.pg.pg8583.service.impl;

import com.electron.mfs.pg.pg8583.service.PgTransactionType1Service;
import com.electron.mfs.pg.pg8583.domain.PgTransactionType1;
import com.electron.mfs.pg.pg8583.repository.PgTransactionType1Repository;
import com.electron.mfs.pg.pg8583.service.dto.PgTransactionType1DTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgTransactionType1Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgTransactionType1}.
 */
@Service
@Transactional
public class PgTransactionType1ServiceImpl implements PgTransactionType1Service {

    private final Logger log = LoggerFactory.getLogger(PgTransactionType1ServiceImpl.class);

    private final PgTransactionType1Repository pgTransactionType1Repository;

    private final PgTransactionType1Mapper pgTransactionType1Mapper;

    public PgTransactionType1ServiceImpl(PgTransactionType1Repository pgTransactionType1Repository, PgTransactionType1Mapper pgTransactionType1Mapper) {
        this.pgTransactionType1Repository = pgTransactionType1Repository;
        this.pgTransactionType1Mapper = pgTransactionType1Mapper;
    }

    /**
     * Save a pgTransactionType1.
     *
     * @param pgTransactionType1DTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgTransactionType1DTO save(PgTransactionType1DTO pgTransactionType1DTO) {
        log.debug("Request to save PgTransactionType1 : {}", pgTransactionType1DTO);
        PgTransactionType1 pgTransactionType1 = pgTransactionType1Mapper.toEntity(pgTransactionType1DTO);
        pgTransactionType1 = pgTransactionType1Repository.save(pgTransactionType1);
        return pgTransactionType1Mapper.toDto(pgTransactionType1);
    }

    /**
     * Get all the pgTransactionType1S.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgTransactionType1DTO> findAll() {
        log.debug("Request to get all PgTransactionType1S");
        return pgTransactionType1Repository.findAll().stream()
            .map(pgTransactionType1Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgTransactionType1 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgTransactionType1DTO> findOne(Long id) {
        log.debug("Request to get PgTransactionType1 : {}", id);
        return pgTransactionType1Repository.findById(id)
            .map(pgTransactionType1Mapper::toDto);
    }

    /**
     * Delete the pgTransactionType1 by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgTransactionType1 : {}", id);
        pgTransactionType1Repository.deleteById(id);
    }
}
