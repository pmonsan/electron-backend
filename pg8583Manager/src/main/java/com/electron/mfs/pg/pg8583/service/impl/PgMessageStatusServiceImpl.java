package com.electron.mfs.pg.pg8583.service.impl;

import com.electron.mfs.pg.pg8583.service.PgMessageStatusService;
import com.electron.mfs.pg.pg8583.domain.PgMessageStatus;
import com.electron.mfs.pg.pg8583.repository.PgMessageStatusRepository;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageStatusDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgMessageStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgMessageStatus}.
 */
@Service
@Transactional
public class PgMessageStatusServiceImpl implements PgMessageStatusService {

    private final Logger log = LoggerFactory.getLogger(PgMessageStatusServiceImpl.class);

    private final PgMessageStatusRepository pgMessageStatusRepository;

    private final PgMessageStatusMapper pgMessageStatusMapper;

    public PgMessageStatusServiceImpl(PgMessageStatusRepository pgMessageStatusRepository, PgMessageStatusMapper pgMessageStatusMapper) {
        this.pgMessageStatusRepository = pgMessageStatusRepository;
        this.pgMessageStatusMapper = pgMessageStatusMapper;
    }

    /**
     * Save a pgMessageStatus.
     *
     * @param pgMessageStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgMessageStatusDTO save(PgMessageStatusDTO pgMessageStatusDTO) {
        log.debug("Request to save PgMessageStatus : {}", pgMessageStatusDTO);
        PgMessageStatus pgMessageStatus = pgMessageStatusMapper.toEntity(pgMessageStatusDTO);
        pgMessageStatus = pgMessageStatusRepository.save(pgMessageStatus);
        return pgMessageStatusMapper.toDto(pgMessageStatus);
    }

    /**
     * Get all the pgMessageStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgMessageStatusDTO> findAll() {
        log.debug("Request to get all PgMessageStatuses");
        return pgMessageStatusRepository.findAll().stream()
            .map(pgMessageStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgMessageStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgMessageStatusDTO> findOne(Long id) {
        log.debug("Request to get PgMessageStatus : {}", id);
        return pgMessageStatusRepository.findById(id)
            .map(pgMessageStatusMapper::toDto);
    }

    /**
     * Delete the pgMessageStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgMessageStatus : {}", id);
        pgMessageStatusRepository.deleteById(id);
    }
}
