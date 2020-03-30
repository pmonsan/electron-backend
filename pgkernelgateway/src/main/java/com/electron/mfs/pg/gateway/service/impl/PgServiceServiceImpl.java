package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgServiceService;
import com.electron.mfs.pg.gateway.domain.PgService;
import com.electron.mfs.pg.gateway.repository.PgServiceRepository;
import com.electron.mfs.pg.gateway.service.dto.PgServiceDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgService}.
 */
@Service
@Transactional
public class PgServiceServiceImpl implements PgServiceService {

    private final Logger log = LoggerFactory.getLogger(PgServiceServiceImpl.class);

    private final PgServiceRepository pgServiceRepository;

    private final PgServiceMapper pgServiceMapper;

    public PgServiceServiceImpl(PgServiceRepository pgServiceRepository, PgServiceMapper pgServiceMapper) {
        this.pgServiceRepository = pgServiceRepository;
        this.pgServiceMapper = pgServiceMapper;
    }

    /**
     * Save a pgService.
     *
     * @param pgServiceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgServiceDTO save(PgServiceDTO pgServiceDTO) {
        log.debug("Request to save PgService : {}", pgServiceDTO);
        PgService pgService = pgServiceMapper.toEntity(pgServiceDTO);
        pgService = pgServiceRepository.save(pgService);
        return pgServiceMapper.toDto(pgService);
    }

    /**
     * Get all the pgServices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgServiceDTO> findAll() {
        log.debug("Request to get all PgServices");
        return pgServiceRepository.findAll().stream()
            .map(pgServiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgServiceDTO> findOne(Long id) {
        log.debug("Request to get PgService : {}", id);
        return pgServiceRepository.findById(id)
            .map(pgServiceMapper::toDto);
    }

    /**
     * Delete the pgService by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgService : {}", id);
        pgServiceRepository.deleteById(id);
    }
}
