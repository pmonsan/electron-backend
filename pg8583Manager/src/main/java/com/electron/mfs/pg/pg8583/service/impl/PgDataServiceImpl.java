package com.electron.mfs.pg.pg8583.service.impl;

import com.electron.mfs.pg.pg8583.service.PgDataService;
import com.electron.mfs.pg.pg8583.domain.PgData;
import com.electron.mfs.pg.pg8583.repository.PgDataRepository;
import com.electron.mfs.pg.pg8583.service.dto.PgDataDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgData}.
 */
@Service
@Transactional
public class PgDataServiceImpl implements PgDataService {

    private final Logger log = LoggerFactory.getLogger(PgDataServiceImpl.class);

    private final PgDataRepository pgDataRepository;

    private final PgDataMapper pgDataMapper;

    public PgDataServiceImpl(PgDataRepository pgDataRepository, PgDataMapper pgDataMapper) {
        this.pgDataRepository = pgDataRepository;
        this.pgDataMapper = pgDataMapper;
    }

    /**
     * Save a pgData.
     *
     * @param pgDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgDataDTO save(PgDataDTO pgDataDTO) {
        log.debug("Request to save PgData : {}", pgDataDTO);
        PgData pgData = pgDataMapper.toEntity(pgDataDTO);
        pgData = pgDataRepository.save(pgData);
        return pgDataMapper.toDto(pgData);
    }

    /**
     * Get all the pgData.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgDataDTO> findAll() {
        log.debug("Request to get all PgData");
        return pgDataRepository.findAll().stream()
            .map(pgDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgDataDTO> findOne(Long id) {
        log.debug("Request to get PgData : {}", id);
        return pgDataRepository.findById(id)
            .map(pgDataMapper::toDto);
    }

    /**
     * Delete the pgData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgData : {}", id);
        pgDataRepository.deleteById(id);
    }
}
