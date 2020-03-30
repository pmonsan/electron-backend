package com.electron.mfs.pg.pg8583client.service.impl;

import com.electron.mfs.pg.pg8583client.service.Pg8583StatusService;
import com.electron.mfs.pg.pg8583client.domain.Pg8583Status;
import com.electron.mfs.pg.pg8583client.repository.Pg8583StatusRepository;
import com.electron.mfs.pg.pg8583client.service.dto.Pg8583StatusDTO;
import com.electron.mfs.pg.pg8583client.service.mapper.Pg8583StatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Pg8583Status}.
 */
@Service
@Transactional
public class Pg8583StatusServiceImpl implements Pg8583StatusService {

    private final Logger log = LoggerFactory.getLogger(Pg8583StatusServiceImpl.class);

    private final Pg8583StatusRepository pg8583StatusRepository;

    private final Pg8583StatusMapper pg8583StatusMapper;

    public Pg8583StatusServiceImpl(Pg8583StatusRepository pg8583StatusRepository, Pg8583StatusMapper pg8583StatusMapper) {
        this.pg8583StatusRepository = pg8583StatusRepository;
        this.pg8583StatusMapper = pg8583StatusMapper;
    }

    /**
     * Save a pg8583Status.
     *
     * @param pg8583StatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pg8583StatusDTO save(Pg8583StatusDTO pg8583StatusDTO) {
        log.debug("Request to save Pg8583Status : {}", pg8583StatusDTO);
        Pg8583Status pg8583Status = pg8583StatusMapper.toEntity(pg8583StatusDTO);
        pg8583Status = pg8583StatusRepository.save(pg8583Status);
        return pg8583StatusMapper.toDto(pg8583Status);
    }

    /**
     * Get all the pg8583Statuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pg8583StatusDTO> findAll() {
        log.debug("Request to get all Pg8583Statuses");
        return pg8583StatusRepository.findAll().stream()
            .map(pg8583StatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pg8583Status by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pg8583StatusDTO> findOne(Long id) {
        log.debug("Request to get Pg8583Status : {}", id);
        return pg8583StatusRepository.findById(id)
            .map(pg8583StatusMapper::toDto);
    }

    /**
     * Delete the pg8583Status by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pg8583Status : {}", id);
        pg8583StatusRepository.deleteById(id);
    }
}
