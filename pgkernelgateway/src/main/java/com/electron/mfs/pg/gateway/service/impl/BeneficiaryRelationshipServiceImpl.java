package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.BeneficiaryRelationshipService;
import com.electron.mfs.pg.gateway.domain.BeneficiaryRelationship;
import com.electron.mfs.pg.gateway.repository.BeneficiaryRelationshipRepository;
import com.electron.mfs.pg.gateway.service.dto.BeneficiaryRelationshipDTO;
import com.electron.mfs.pg.gateway.service.mapper.BeneficiaryRelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BeneficiaryRelationship}.
 */
@Service
@Transactional
public class BeneficiaryRelationshipServiceImpl implements BeneficiaryRelationshipService {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryRelationshipServiceImpl.class);

    private final BeneficiaryRelationshipRepository beneficiaryRelationshipRepository;

    private final BeneficiaryRelationshipMapper beneficiaryRelationshipMapper;

    public BeneficiaryRelationshipServiceImpl(BeneficiaryRelationshipRepository beneficiaryRelationshipRepository, BeneficiaryRelationshipMapper beneficiaryRelationshipMapper) {
        this.beneficiaryRelationshipRepository = beneficiaryRelationshipRepository;
        this.beneficiaryRelationshipMapper = beneficiaryRelationshipMapper;
    }

    /**
     * Save a beneficiaryRelationship.
     *
     * @param beneficiaryRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BeneficiaryRelationshipDTO save(BeneficiaryRelationshipDTO beneficiaryRelationshipDTO) {
        log.debug("Request to save BeneficiaryRelationship : {}", beneficiaryRelationshipDTO);
        BeneficiaryRelationship beneficiaryRelationship = beneficiaryRelationshipMapper.toEntity(beneficiaryRelationshipDTO);
        beneficiaryRelationship = beneficiaryRelationshipRepository.save(beneficiaryRelationship);
        return beneficiaryRelationshipMapper.toDto(beneficiaryRelationship);
    }

    /**
     * Get all the beneficiaryRelationships.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryRelationshipDTO> findAll() {
        log.debug("Request to get all BeneficiaryRelationships");
        return beneficiaryRelationshipRepository.findAll().stream()
            .map(beneficiaryRelationshipMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one beneficiaryRelationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BeneficiaryRelationshipDTO> findOne(Long id) {
        log.debug("Request to get BeneficiaryRelationship : {}", id);
        return beneficiaryRelationshipRepository.findById(id)
            .map(beneficiaryRelationshipMapper::toDto);
    }

    /**
     * Delete the beneficiaryRelationship by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BeneficiaryRelationship : {}", id);
        beneficiaryRelationshipRepository.deleteById(id);
    }
}
