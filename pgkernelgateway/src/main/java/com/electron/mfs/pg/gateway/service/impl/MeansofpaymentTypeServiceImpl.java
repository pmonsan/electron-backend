package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.MeansofpaymentTypeService;
import com.electron.mfs.pg.gateway.domain.MeansofpaymentType;
import com.electron.mfs.pg.gateway.repository.MeansofpaymentTypeRepository;
import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.MeansofpaymentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MeansofpaymentType}.
 */
@Service
@Transactional
public class MeansofpaymentTypeServiceImpl implements MeansofpaymentTypeService {

    private final Logger log = LoggerFactory.getLogger(MeansofpaymentTypeServiceImpl.class);

    private final MeansofpaymentTypeRepository meansofpaymentTypeRepository;

    private final MeansofpaymentTypeMapper meansofpaymentTypeMapper;

    public MeansofpaymentTypeServiceImpl(MeansofpaymentTypeRepository meansofpaymentTypeRepository, MeansofpaymentTypeMapper meansofpaymentTypeMapper) {
        this.meansofpaymentTypeRepository = meansofpaymentTypeRepository;
        this.meansofpaymentTypeMapper = meansofpaymentTypeMapper;
    }

    /**
     * Save a meansofpaymentType.
     *
     * @param meansofpaymentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MeansofpaymentTypeDTO save(MeansofpaymentTypeDTO meansofpaymentTypeDTO) {
        log.debug("Request to save MeansofpaymentType : {}", meansofpaymentTypeDTO);
        MeansofpaymentType meansofpaymentType = meansofpaymentTypeMapper.toEntity(meansofpaymentTypeDTO);
        meansofpaymentType = meansofpaymentTypeRepository.save(meansofpaymentType);
        return meansofpaymentTypeMapper.toDto(meansofpaymentType);
    }

    /**
     * Get all the meansofpaymentTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeansofpaymentTypeDTO> findAll() {
        log.debug("Request to get all MeansofpaymentTypes");
        return meansofpaymentTypeRepository.findAll().stream()
            .map(meansofpaymentTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one meansofpaymentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MeansofpaymentTypeDTO> findOne(Long id) {
        log.debug("Request to get MeansofpaymentType : {}", id);
        return meansofpaymentTypeRepository.findById(id)
            .map(meansofpaymentTypeMapper::toDto);
    }

    /**
     * Delete the meansofpaymentType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeansofpaymentType : {}", id);
        meansofpaymentTypeRepository.deleteById(id);
    }
}
