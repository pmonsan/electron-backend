package com.electron.mfs.pg.pg8583.service.impl;

import com.electron.mfs.pg.pg8583.service.PgMessageService;
import com.electron.mfs.pg.pg8583.domain.PgMessage;
import com.electron.mfs.pg.pg8583.repository.PgMessageRepository;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgMessage}.
 */
@Service
@Transactional
public class PgMessageServiceImpl implements PgMessageService {

    private final Logger log = LoggerFactory.getLogger(PgMessageServiceImpl.class);

    private final PgMessageRepository pgMessageRepository;

    private final PgMessageMapper pgMessageMapper;

    public PgMessageServiceImpl(PgMessageRepository pgMessageRepository, PgMessageMapper pgMessageMapper) {
        this.pgMessageRepository = pgMessageRepository;
        this.pgMessageMapper = pgMessageMapper;
    }

    /**
     * Save a pgMessage.
     *
     * @param pgMessageDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgMessageDTO save(PgMessageDTO pgMessageDTO) {
        log.debug("Request to save PgMessage : {}", pgMessageDTO);
        PgMessage pgMessage = pgMessageMapper.toEntity(pgMessageDTO);
        pgMessage = pgMessageRepository.save(pgMessage);
        return pgMessageMapper.toDto(pgMessage);
    }

    /**
     * Get all the pgMessages.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgMessageDTO> findAll() {
        log.debug("Request to get all PgMessages");
        return pgMessageRepository.findAll().stream()
            .map(pgMessageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgMessageDTO> findOne(Long id) {
        log.debug("Request to get PgMessage : {}", id);
        return pgMessageRepository.findById(id)
            .map(pgMessageMapper::toDto);
    }

    /**
     * Delete the pgMessage by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgMessage : {}", id);
        pgMessageRepository.deleteById(id);
    }
}
