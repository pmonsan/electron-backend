package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgChannelService;
import com.electron.mfs.pg.gateway.domain.PgChannel;
import com.electron.mfs.pg.gateway.repository.PgChannelRepository;
import com.electron.mfs.pg.gateway.service.dto.PgChannelDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgChannel}.
 */
@Service
@Transactional
public class PgChannelServiceImpl implements PgChannelService {

    private final Logger log = LoggerFactory.getLogger(PgChannelServiceImpl.class);

    private final PgChannelRepository pgChannelRepository;

    private final PgChannelMapper pgChannelMapper;

    public PgChannelServiceImpl(PgChannelRepository pgChannelRepository, PgChannelMapper pgChannelMapper) {
        this.pgChannelRepository = pgChannelRepository;
        this.pgChannelMapper = pgChannelMapper;
    }

    /**
     * Save a pgChannel.
     *
     * @param pgChannelDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgChannelDTO save(PgChannelDTO pgChannelDTO) {
        log.debug("Request to save PgChannel : {}", pgChannelDTO);
        PgChannel pgChannel = pgChannelMapper.toEntity(pgChannelDTO);
        pgChannel = pgChannelRepository.save(pgChannel);
        return pgChannelMapper.toDto(pgChannel);
    }

    /**
     * Get all the pgChannels.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgChannelDTO> findAll() {
        log.debug("Request to get all PgChannels");
        return pgChannelRepository.findAll().stream()
            .map(pgChannelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgChannel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgChannelDTO> findOne(Long id) {
        log.debug("Request to get PgChannel : {}", id);
        return pgChannelRepository.findById(id)
            .map(pgChannelMapper::toDto);
    }

    /**
     * Delete the pgChannel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgChannel : {}", id);
        pgChannelRepository.deleteById(id);
    }
}
