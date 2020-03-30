package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.ForexService;
import com.electron.mfs.pg.mdm.domain.Forex;
import com.electron.mfs.pg.mdm.repository.ForexRepository;
import com.electron.mfs.pg.mdm.service.dto.ForexDTO;
import com.electron.mfs.pg.mdm.service.mapper.ForexMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Forex}.
 */
@Service
@Transactional
public class ForexServiceImpl implements ForexService {

    private final Logger log = LoggerFactory.getLogger(ForexServiceImpl.class);

    private final ForexRepository forexRepository;

    private final ForexMapper forexMapper;

    public ForexServiceImpl(ForexRepository forexRepository, ForexMapper forexMapper) {
        this.forexRepository = forexRepository;
        this.forexMapper = forexMapper;
    }

    /**
     * Save a forex.
     *
     * @param forexDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ForexDTO save(ForexDTO forexDTO) {
        log.debug("Request to save Forex : {}", forexDTO);
        Forex forex = forexMapper.toEntity(forexDTO);
        forex = forexRepository.save(forex);
        return forexMapper.toDto(forex);
    }

    /**
     * Get all the forexes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ForexDTO> findAll() {
        log.debug("Request to get all Forexes");
        return forexRepository.findAll().stream()
            .map(forexMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one forex by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ForexDTO> findOne(Long id) {
        log.debug("Request to get Forex : {}", id);
        return forexRepository.findById(id)
            .map(forexMapper::toDto);
    }

    /**
     * Delete the forex by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Forex : {}", id);
        forexRepository.deleteById(id);
    }
}
