package com.electron.mfs.pg.limits.service.impl;

import com.electron.mfs.pg.limits.service.LimitTypeService;
import com.electron.mfs.pg.limits.domain.LimitType;
import com.electron.mfs.pg.limits.repository.LimitTypeRepository;
import com.electron.mfs.pg.limits.service.dto.LimitTypeDTO;
import com.electron.mfs.pg.limits.service.mapper.LimitTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LimitType}.
 */
@Service
@Transactional
public class LimitTypeServiceImpl implements LimitTypeService {

    private final Logger log = LoggerFactory.getLogger(LimitTypeServiceImpl.class);

    private final LimitTypeRepository limitTypeRepository;

    private final LimitTypeMapper limitTypeMapper;

    public LimitTypeServiceImpl(LimitTypeRepository limitTypeRepository, LimitTypeMapper limitTypeMapper) {
        this.limitTypeRepository = limitTypeRepository;
        this.limitTypeMapper = limitTypeMapper;
    }

    /**
     * Save a limitType.
     *
     * @param limitTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LimitTypeDTO save(LimitTypeDTO limitTypeDTO) {
        log.debug("Request to save LimitType : {}", limitTypeDTO);
        LimitType limitType = limitTypeMapper.toEntity(limitTypeDTO);
        limitType = limitTypeRepository.save(limitType);
        return limitTypeMapper.toDto(limitType);
    }

    /**
     * Get all the limitTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LimitTypeDTO> findAll() {
        log.debug("Request to get all LimitTypes");
        return limitTypeRepository.findAll().stream()
            .map(limitTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one limitType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LimitTypeDTO> findOne(Long id) {
        log.debug("Request to get LimitType : {}", id);
        return limitTypeRepository.findById(id)
            .map(limitTypeMapper::toDto);
    }

    /**
     * Delete the limitType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LimitType : {}", id);
        limitTypeRepository.deleteById(id);
    }
}
