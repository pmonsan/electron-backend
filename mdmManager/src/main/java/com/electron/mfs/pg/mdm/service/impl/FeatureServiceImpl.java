package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.FeatureService;
import com.electron.mfs.pg.mdm.domain.Feature;
import com.electron.mfs.pg.mdm.repository.FeatureRepository;
import com.electron.mfs.pg.mdm.service.dto.FeatureDTO;
import com.electron.mfs.pg.mdm.service.mapper.FeatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Feature}.
 */
@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {

    private final Logger log = LoggerFactory.getLogger(FeatureServiceImpl.class);

    private final FeatureRepository featureRepository;

    private final FeatureMapper featureMapper;

    public FeatureServiceImpl(FeatureRepository featureRepository, FeatureMapper featureMapper) {
        this.featureRepository = featureRepository;
        this.featureMapper = featureMapper;
    }

    /**
     * Save a feature.
     *
     * @param featureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FeatureDTO save(FeatureDTO featureDTO) {
        log.debug("Request to save Feature : {}", featureDTO);
        Feature feature = featureMapper.toEntity(featureDTO);
        feature = featureRepository.save(feature);
        return featureMapper.toDto(feature);
    }

    /**
     * Get all the features.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FeatureDTO> findAll() {
        log.debug("Request to get all Features");
        return featureRepository.findAll().stream()
            .map(featureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one feature by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FeatureDTO> findOne(Long id) {
        log.debug("Request to get Feature : {}", id);
        return featureRepository.findById(id)
            .map(featureMapper::toDto);
    }

    /**
     * Delete the feature by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feature : {}", id);
        featureRepository.deleteById(id);
    }
}
