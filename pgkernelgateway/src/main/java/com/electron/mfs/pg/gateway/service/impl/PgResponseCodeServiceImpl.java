package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgResponseCodeService;
import com.electron.mfs.pg.gateway.domain.PgResponseCode;
import com.electron.mfs.pg.gateway.repository.PgResponseCodeRepository;
import com.electron.mfs.pg.gateway.service.dto.PgResponseCodeDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgResponseCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgResponseCode}.
 */
@Service
@Transactional
public class PgResponseCodeServiceImpl implements PgResponseCodeService {

    private final Logger log = LoggerFactory.getLogger(PgResponseCodeServiceImpl.class);

    private final PgResponseCodeRepository pgResponseCodeRepository;

    private final PgResponseCodeMapper pgResponseCodeMapper;

    public PgResponseCodeServiceImpl(PgResponseCodeRepository pgResponseCodeRepository, PgResponseCodeMapper pgResponseCodeMapper) {
        this.pgResponseCodeRepository = pgResponseCodeRepository;
        this.pgResponseCodeMapper = pgResponseCodeMapper;
    }

    /**
     * Save a pgResponseCode.
     *
     * @param pgResponseCodeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgResponseCodeDTO save(PgResponseCodeDTO pgResponseCodeDTO) {
        log.debug("Request to save PgResponseCode : {}", pgResponseCodeDTO);
        PgResponseCode pgResponseCode = pgResponseCodeMapper.toEntity(pgResponseCodeDTO);
        pgResponseCode = pgResponseCodeRepository.save(pgResponseCode);
        return pgResponseCodeMapper.toDto(pgResponseCode);
    }

    /**
     * Get all the pgResponseCodes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgResponseCodeDTO> findAll() {
        log.debug("Request to get all PgResponseCodes");
        return pgResponseCodeRepository.findAll().stream()
            .map(pgResponseCodeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgResponseCode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgResponseCodeDTO> findOne(Long id) {
        log.debug("Request to get PgResponseCode : {}", id);
        return pgResponseCodeRepository.findById(id)
            .map(pgResponseCodeMapper::toDto);
    }

    /**
     * Delete the pgResponseCode by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgResponseCode : {}", id);
        pgResponseCodeRepository.deleteById(id);
    }
}
