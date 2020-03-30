package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.PartnerTypeService;
import com.electron.mfs.pg.mdm.domain.PartnerType;
import com.electron.mfs.pg.mdm.repository.PartnerTypeRepository;
import com.electron.mfs.pg.mdm.service.dto.PartnerTypeDTO;
import com.electron.mfs.pg.mdm.service.mapper.PartnerTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartnerType}.
 */
@Service
@Transactional
public class PartnerTypeServiceImpl implements PartnerTypeService {

    private final Logger log = LoggerFactory.getLogger(PartnerTypeServiceImpl.class);

    private final PartnerTypeRepository partnerTypeRepository;

    private final PartnerTypeMapper partnerTypeMapper;

    public PartnerTypeServiceImpl(PartnerTypeRepository partnerTypeRepository, PartnerTypeMapper partnerTypeMapper) {
        this.partnerTypeRepository = partnerTypeRepository;
        this.partnerTypeMapper = partnerTypeMapper;
    }

    /**
     * Save a partnerType.
     *
     * @param partnerTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartnerTypeDTO save(PartnerTypeDTO partnerTypeDTO) {
        log.debug("Request to save PartnerType : {}", partnerTypeDTO);
        PartnerType partnerType = partnerTypeMapper.toEntity(partnerTypeDTO);
        partnerType = partnerTypeRepository.save(partnerType);
        return partnerTypeMapper.toDto(partnerType);
    }

    /**
     * Get all the partnerTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartnerTypeDTO> findAll() {
        log.debug("Request to get all PartnerTypes");
        return partnerTypeRepository.findAll().stream()
            .map(partnerTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partnerType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartnerTypeDTO> findOne(Long id) {
        log.debug("Request to get PartnerType : {}", id);
        return partnerTypeRepository.findById(id)
            .map(partnerTypeMapper::toDto);
    }

    /**
     * Delete the partnerType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartnerType : {}", id);
        partnerTypeRepository.deleteById(id);
    }
}
