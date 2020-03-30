package com.electron.mfs.pg.pg8583.service.impl;

import com.electron.mfs.pg.pg8583.service.PgMessageModelDataService;
import com.electron.mfs.pg.pg8583.domain.PgMessageModelData;
import com.electron.mfs.pg.pg8583.repository.PgMessageModelDataRepository;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageModelDataDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgMessageModelDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgMessageModelData}.
 */
@Service
@Transactional
public class PgMessageModelDataServiceImpl implements PgMessageModelDataService {

    private final Logger log = LoggerFactory.getLogger(PgMessageModelDataServiceImpl.class);

    private final PgMessageModelDataRepository pgMessageModelDataRepository;

    private final PgMessageModelDataMapper pgMessageModelDataMapper;

    public PgMessageModelDataServiceImpl(PgMessageModelDataRepository pgMessageModelDataRepository, PgMessageModelDataMapper pgMessageModelDataMapper) {
        this.pgMessageModelDataRepository = pgMessageModelDataRepository;
        this.pgMessageModelDataMapper = pgMessageModelDataMapper;
    }

    /**
     * Save a pgMessageModelData.
     *
     * @param pgMessageModelDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgMessageModelDataDTO save(PgMessageModelDataDTO pgMessageModelDataDTO) {
        log.debug("Request to save PgMessageModelData : {}", pgMessageModelDataDTO);
        PgMessageModelData pgMessageModelData = pgMessageModelDataMapper.toEntity(pgMessageModelDataDTO);
        pgMessageModelData = pgMessageModelDataRepository.save(pgMessageModelData);
        return pgMessageModelDataMapper.toDto(pgMessageModelData);
    }

    /**
     * Get all the pgMessageModelData.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgMessageModelDataDTO> findAll() {
        log.debug("Request to get all PgMessageModelData");
        return pgMessageModelDataRepository.findAll().stream()
            .map(pgMessageModelDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgMessageModelData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgMessageModelDataDTO> findOne(Long id) {
        log.debug("Request to get PgMessageModelData : {}", id);
        return pgMessageModelDataRepository.findById(id)
            .map(pgMessageModelDataMapper::toDto);
    }

    /**
     * Delete the pgMessageModelData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgMessageModelData : {}", id);
        pgMessageModelDataRepository.deleteById(id);
    }
}
