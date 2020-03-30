package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgDetailMessageService;
import com.electron.mfs.pg.gateway.domain.PgDetailMessage;
import com.electron.mfs.pg.gateway.repository.PgDetailMessageRepository;
import com.electron.mfs.pg.gateway.service.dto.PgDetailMessageDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgDetailMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgDetailMessage}.
 */
@Service
@Transactional
public class PgDetailMessageServiceImpl implements PgDetailMessageService {

    private final Logger log = LoggerFactory.getLogger(PgDetailMessageServiceImpl.class);

    private final PgDetailMessageRepository pgDetailMessageRepository;

    private final PgDetailMessageMapper pgDetailMessageMapper;

    public PgDetailMessageServiceImpl(PgDetailMessageRepository pgDetailMessageRepository, PgDetailMessageMapper pgDetailMessageMapper) {
        this.pgDetailMessageRepository = pgDetailMessageRepository;
        this.pgDetailMessageMapper = pgDetailMessageMapper;
    }

    /**
     * Save a pgDetailMessage.
     *
     * @param pgDetailMessageDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgDetailMessageDTO save(PgDetailMessageDTO pgDetailMessageDTO) {
        log.debug("Request to save PgDetailMessage : {}", pgDetailMessageDTO);
        PgDetailMessage pgDetailMessage = pgDetailMessageMapper.toEntity(pgDetailMessageDTO);
        pgDetailMessage = pgDetailMessageRepository.save(pgDetailMessage);
        return pgDetailMessageMapper.toDto(pgDetailMessage);
    }

    /**
     * Get all the pgDetailMessages.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgDetailMessageDTO> findAll() {
        log.debug("Request to get all PgDetailMessages");
        return pgDetailMessageRepository.findAll().stream()
            .map(pgDetailMessageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgDetailMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgDetailMessageDTO> findOne(Long id) {
        log.debug("Request to get PgDetailMessage : {}", id);
        return pgDetailMessageRepository.findById(id)
            .map(pgDetailMessageMapper::toDto);
    }

    /**
     * Delete the pgDetailMessage by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgDetailMessage : {}", id);
        pgDetailMessageRepository.deleteById(id);
    }
}
