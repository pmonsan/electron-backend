package com.electron.mfs.pg.pg8583client.service.impl;

import com.electron.mfs.pg.pg8583client.service.Pg8583CallbackService;
import com.electron.mfs.pg.pg8583client.domain.Pg8583Callback;
import com.electron.mfs.pg.pg8583client.repository.Pg8583CallbackRepository;
import com.electron.mfs.pg.pg8583client.service.dto.Pg8583CallbackDTO;
import com.electron.mfs.pg.pg8583client.service.mapper.Pg8583CallbackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Pg8583Callback}.
 */
@Service
@Transactional
public class Pg8583CallbackServiceImpl implements Pg8583CallbackService {

    private final Logger log = LoggerFactory.getLogger(Pg8583CallbackServiceImpl.class);

    private final Pg8583CallbackRepository pg8583CallbackRepository;

    private final Pg8583CallbackMapper pg8583CallbackMapper;

    public Pg8583CallbackServiceImpl(Pg8583CallbackRepository pg8583CallbackRepository, Pg8583CallbackMapper pg8583CallbackMapper) {
        this.pg8583CallbackRepository = pg8583CallbackRepository;
        this.pg8583CallbackMapper = pg8583CallbackMapper;
    }

    /**
     * Save a pg8583Callback.
     *
     * @param pg8583CallbackDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pg8583CallbackDTO save(Pg8583CallbackDTO pg8583CallbackDTO) {
        log.debug("Request to save Pg8583Callback : {}", pg8583CallbackDTO);
        Pg8583Callback pg8583Callback = pg8583CallbackMapper.toEntity(pg8583CallbackDTO);
        pg8583Callback = pg8583CallbackRepository.save(pg8583Callback);
        return pg8583CallbackMapper.toDto(pg8583Callback);
    }

    /**
     * Get all the pg8583Callbacks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pg8583CallbackDTO> findAll() {
        log.debug("Request to get all Pg8583Callbacks");
        return pg8583CallbackRepository.findAll().stream()
            .map(pg8583CallbackMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pg8583Callback by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pg8583CallbackDTO> findOne(Long id) {
        log.debug("Request to get Pg8583Callback : {}", id);
        return pg8583CallbackRepository.findById(id)
            .map(pg8583CallbackMapper::toDto);
    }

    /**
     * Delete the pg8583Callback by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pg8583Callback : {}", id);
        pg8583CallbackRepository.deleteById(id);
    }
}
