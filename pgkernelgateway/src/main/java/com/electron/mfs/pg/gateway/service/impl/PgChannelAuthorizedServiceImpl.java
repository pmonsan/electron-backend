package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgChannelAuthorizedService;
import com.electron.mfs.pg.gateway.domain.PgChannelAuthorized;
import com.electron.mfs.pg.gateway.repository.PgChannelAuthorizedRepository;
import com.electron.mfs.pg.gateway.service.dto.PgChannelAuthorizedDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgChannelAuthorizedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgChannelAuthorized}.
 */
@Service
@Transactional
public class PgChannelAuthorizedServiceImpl implements PgChannelAuthorizedService {

    private final Logger log = LoggerFactory.getLogger(PgChannelAuthorizedServiceImpl.class);

    private final PgChannelAuthorizedRepository pgChannelAuthorizedRepository;

    private final PgChannelAuthorizedMapper pgChannelAuthorizedMapper;

    public PgChannelAuthorizedServiceImpl(PgChannelAuthorizedRepository pgChannelAuthorizedRepository, PgChannelAuthorizedMapper pgChannelAuthorizedMapper) {
        this.pgChannelAuthorizedRepository = pgChannelAuthorizedRepository;
        this.pgChannelAuthorizedMapper = pgChannelAuthorizedMapper;
    }

    /**
     * Save a pgChannelAuthorized.
     *
     * @param pgChannelAuthorizedDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgChannelAuthorizedDTO save(PgChannelAuthorizedDTO pgChannelAuthorizedDTO) {
        log.debug("Request to save PgChannelAuthorized : {}", pgChannelAuthorizedDTO);
        PgChannelAuthorized pgChannelAuthorized = pgChannelAuthorizedMapper.toEntity(pgChannelAuthorizedDTO);
        pgChannelAuthorized = pgChannelAuthorizedRepository.save(pgChannelAuthorized);
        return pgChannelAuthorizedMapper.toDto(pgChannelAuthorized);
    }

    /**
     * Get all the pgChannelAuthorizeds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgChannelAuthorizedDTO> findAll() {
        log.debug("Request to get all PgChannelAuthorizeds");
        return pgChannelAuthorizedRepository.findAll().stream()
            .map(pgChannelAuthorizedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgChannelAuthorized by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgChannelAuthorizedDTO> findOne(Long id) {
        log.debug("Request to get PgChannelAuthorized : {}", id);
        return pgChannelAuthorizedRepository.findById(id)
            .map(pgChannelAuthorizedMapper::toDto);
    }

    /**
     * Delete the pgChannelAuthorized by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgChannelAuthorized : {}", id);
        pgChannelAuthorizedRepository.deleteById(id);
    }
}
