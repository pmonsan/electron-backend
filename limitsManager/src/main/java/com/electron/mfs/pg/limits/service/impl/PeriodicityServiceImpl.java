package com.electron.mfs.pg.limits.service.impl;

import com.electron.mfs.pg.limits.service.PeriodicityService;
import com.electron.mfs.pg.limits.domain.Periodicity;
import com.electron.mfs.pg.limits.repository.PeriodicityRepository;
import com.electron.mfs.pg.limits.service.dto.PeriodicityDTO;
import com.electron.mfs.pg.limits.service.mapper.PeriodicityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Periodicity}.
 */
@Service
@Transactional
public class PeriodicityServiceImpl implements PeriodicityService {

    private final Logger log = LoggerFactory.getLogger(PeriodicityServiceImpl.class);

    private final PeriodicityRepository periodicityRepository;

    private final PeriodicityMapper periodicityMapper;

    public PeriodicityServiceImpl(PeriodicityRepository periodicityRepository, PeriodicityMapper periodicityMapper) {
        this.periodicityRepository = periodicityRepository;
        this.periodicityMapper = periodicityMapper;
    }

    /**
     * Save a periodicity.
     *
     * @param periodicityDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PeriodicityDTO save(PeriodicityDTO periodicityDTO) {
        log.debug("Request to save Periodicity : {}", periodicityDTO);
        Periodicity periodicity = periodicityMapper.toEntity(periodicityDTO);
        periodicity = periodicityRepository.save(periodicity);
        return periodicityMapper.toDto(periodicity);
    }

    /**
     * Get all the periodicities.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PeriodicityDTO> findAll() {
        log.debug("Request to get all Periodicities");
        return periodicityRepository.findAll().stream()
            .map(periodicityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one periodicity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodicityDTO> findOne(Long id) {
        log.debug("Request to get Periodicity : {}", id);
        return periodicityRepository.findById(id)
            .map(periodicityMapper::toDto);
    }

    /**
     * Delete the periodicity by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Periodicity : {}", id);
        periodicityRepository.deleteById(id);
    }
}
