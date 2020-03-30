package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.ActivityAreaService;
import com.electron.mfs.pg.gateway.domain.ActivityArea;
import com.electron.mfs.pg.gateway.repository.ActivityAreaRepository;
import com.electron.mfs.pg.gateway.service.dto.ActivityAreaDTO;
import com.electron.mfs.pg.gateway.service.mapper.ActivityAreaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ActivityArea}.
 */
@Service
@Transactional
public class ActivityAreaServiceImpl implements ActivityAreaService {

    private final Logger log = LoggerFactory.getLogger(ActivityAreaServiceImpl.class);

    private final ActivityAreaRepository activityAreaRepository;

    private final ActivityAreaMapper activityAreaMapper;

    public ActivityAreaServiceImpl(ActivityAreaRepository activityAreaRepository, ActivityAreaMapper activityAreaMapper) {
        this.activityAreaRepository = activityAreaRepository;
        this.activityAreaMapper = activityAreaMapper;
    }

    /**
     * Save a activityArea.
     *
     * @param activityAreaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ActivityAreaDTO save(ActivityAreaDTO activityAreaDTO) {
        log.debug("Request to save ActivityArea : {}", activityAreaDTO);
        ActivityArea activityArea = activityAreaMapper.toEntity(activityAreaDTO);
        activityArea = activityAreaRepository.save(activityArea);
        return activityAreaMapper.toDto(activityArea);
    }

    /**
     * Get all the activityAreas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActivityAreaDTO> findAll() {
        log.debug("Request to get all ActivityAreas");
        return activityAreaRepository.findAll().stream()
            .map(activityAreaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one activityArea by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityAreaDTO> findOne(Long id) {
        log.debug("Request to get ActivityArea : {}", id);
        return activityAreaRepository.findById(id)
            .map(activityAreaMapper::toDto);
    }

    /**
     * Delete the activityArea by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityArea : {}", id);
        activityAreaRepository.deleteById(id);
    }
}
