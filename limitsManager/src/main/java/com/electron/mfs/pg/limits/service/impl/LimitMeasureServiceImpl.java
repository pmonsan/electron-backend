package com.electron.mfs.pg.limits.service.impl;

import com.electron.mfs.pg.limits.service.LimitMeasureService;
import com.electron.mfs.pg.limits.domain.LimitMeasure;
import com.electron.mfs.pg.limits.repository.LimitMeasureRepository;
import com.electron.mfs.pg.limits.service.dto.LimitMeasureDTO;
import com.electron.mfs.pg.limits.service.mapper.LimitMeasureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LimitMeasure}.
 */
@Service
@Transactional
public class LimitMeasureServiceImpl implements LimitMeasureService {

    private final Logger log = LoggerFactory.getLogger(LimitMeasureServiceImpl.class);

    private final LimitMeasureRepository limitMeasureRepository;

    private final LimitMeasureMapper limitMeasureMapper;

    public LimitMeasureServiceImpl(LimitMeasureRepository limitMeasureRepository, LimitMeasureMapper limitMeasureMapper) {
        this.limitMeasureRepository = limitMeasureRepository;
        this.limitMeasureMapper = limitMeasureMapper;
    }

    /**
     * Save a limitMeasure.
     *
     * @param limitMeasureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LimitMeasureDTO save(LimitMeasureDTO limitMeasureDTO) {
        log.debug("Request to save LimitMeasure : {}", limitMeasureDTO);
        LimitMeasure limitMeasure = limitMeasureMapper.toEntity(limitMeasureDTO);
        limitMeasure = limitMeasureRepository.save(limitMeasure);
        return limitMeasureMapper.toDto(limitMeasure);
    }

    /**
     * Get all the limitMeasures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LimitMeasureDTO> findAll() {
        log.debug("Request to get all LimitMeasures");
        return limitMeasureRepository.findAll().stream()
            .map(limitMeasureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one limitMeasure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LimitMeasureDTO> findOne(Long id) {
        log.debug("Request to get LimitMeasure : {}", id);
        return limitMeasureRepository.findById(id)
            .map(limitMeasureMapper::toDto);
    }

    /**
     * Delete the limitMeasure by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LimitMeasure : {}", id);
        limitMeasureRepository.deleteById(id);
    }
}
