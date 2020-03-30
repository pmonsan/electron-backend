package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.BeneficiaryTypeService;
import com.electron.mfs.pg.mdm.domain.BeneficiaryType;
import com.electron.mfs.pg.mdm.repository.BeneficiaryTypeRepository;
import com.electron.mfs.pg.mdm.service.dto.BeneficiaryTypeDTO;
import com.electron.mfs.pg.mdm.service.mapper.BeneficiaryTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BeneficiaryType}.
 */
@Service
@Transactional
public class BeneficiaryTypeServiceImpl implements BeneficiaryTypeService {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryTypeServiceImpl.class);

    private final BeneficiaryTypeRepository beneficiaryTypeRepository;

    private final BeneficiaryTypeMapper beneficiaryTypeMapper;

    public BeneficiaryTypeServiceImpl(BeneficiaryTypeRepository beneficiaryTypeRepository, BeneficiaryTypeMapper beneficiaryTypeMapper) {
        this.beneficiaryTypeRepository = beneficiaryTypeRepository;
        this.beneficiaryTypeMapper = beneficiaryTypeMapper;
    }

    /**
     * Save a beneficiaryType.
     *
     * @param beneficiaryTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BeneficiaryTypeDTO save(BeneficiaryTypeDTO beneficiaryTypeDTO) {
        log.debug("Request to save BeneficiaryType : {}", beneficiaryTypeDTO);
        BeneficiaryType beneficiaryType = beneficiaryTypeMapper.toEntity(beneficiaryTypeDTO);
        beneficiaryType = beneficiaryTypeRepository.save(beneficiaryType);
        return beneficiaryTypeMapper.toDto(beneficiaryType);
    }

    /**
     * Get all the beneficiaryTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryTypeDTO> findAll() {
        log.debug("Request to get all BeneficiaryTypes");
        return beneficiaryTypeRepository.findAll().stream()
            .map(beneficiaryTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one beneficiaryType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BeneficiaryTypeDTO> findOne(Long id) {
        log.debug("Request to get BeneficiaryType : {}", id);
        return beneficiaryTypeRepository.findById(id)
            .map(beneficiaryTypeMapper::toDto);
    }

    /**
     * Delete the beneficiaryType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BeneficiaryType : {}", id);
        beneficiaryTypeRepository.deleteById(id);
    }
}
