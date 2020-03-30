package com.electron.mfs.pg.pg8583client.service.impl;

import com.electron.mfs.pg.pg8583client.service.Pg8583RequestService;
import com.electron.mfs.pg.pg8583client.domain.Pg8583Request;
import com.electron.mfs.pg.pg8583client.repository.Pg8583RequestRepository;
import com.electron.mfs.pg.pg8583client.service.dto.Pg8583RequestDTO;
import com.electron.mfs.pg.pg8583client.service.mapper.Pg8583RequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Pg8583Request}.
 */
@Service
@Transactional
public class Pg8583RequestServiceImpl implements Pg8583RequestService {

    private final Logger log = LoggerFactory.getLogger(Pg8583RequestServiceImpl.class);

    private final Pg8583RequestRepository pg8583RequestRepository;

    private final Pg8583RequestMapper pg8583RequestMapper;

    public Pg8583RequestServiceImpl(Pg8583RequestRepository pg8583RequestRepository, Pg8583RequestMapper pg8583RequestMapper) {
        this.pg8583RequestRepository = pg8583RequestRepository;
        this.pg8583RequestMapper = pg8583RequestMapper;
    }

    /**
     * Save a pg8583Request.
     *
     * @param pg8583RequestDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pg8583RequestDTO save(Pg8583RequestDTO pg8583RequestDTO) {
        log.debug("Request to save Pg8583Request : {}", pg8583RequestDTO);
        Pg8583Request pg8583Request = pg8583RequestMapper.toEntity(pg8583RequestDTO);
        pg8583Request = pg8583RequestRepository.save(pg8583Request);
        return pg8583RequestMapper.toDto(pg8583Request);
    }

    /**
     * Get all the pg8583Requests.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pg8583RequestDTO> findAll() {
        log.debug("Request to get all Pg8583Requests");
        return pg8583RequestRepository.findAll().stream()
            .map(pg8583RequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pg8583Request by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pg8583RequestDTO> findOne(Long id) {
        log.debug("Request to get Pg8583Request : {}", id);
        return pg8583RequestRepository.findById(id)
            .map(pg8583RequestMapper::toDto);
    }

    /**
     * Delete the pg8583Request by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pg8583Request : {}", id);
        pg8583RequestRepository.deleteById(id);
    }
}
