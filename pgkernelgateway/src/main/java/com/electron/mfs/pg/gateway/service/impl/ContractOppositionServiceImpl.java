package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.ContractOppositionService;
import com.electron.mfs.pg.gateway.domain.ContractOpposition;
import com.electron.mfs.pg.gateway.repository.ContractOppositionRepository;
import com.electron.mfs.pg.gateway.service.dto.ContractOppositionDTO;
import com.electron.mfs.pg.gateway.service.mapper.ContractOppositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ContractOpposition}.
 */
@Service
@Transactional
public class ContractOppositionServiceImpl implements ContractOppositionService {

    private final Logger log = LoggerFactory.getLogger(ContractOppositionServiceImpl.class);

    private final ContractOppositionRepository contractOppositionRepository;

    private final ContractOppositionMapper contractOppositionMapper;

    public ContractOppositionServiceImpl(ContractOppositionRepository contractOppositionRepository, ContractOppositionMapper contractOppositionMapper) {
        this.contractOppositionRepository = contractOppositionRepository;
        this.contractOppositionMapper = contractOppositionMapper;
    }

    /**
     * Save a contractOpposition.
     *
     * @param contractOppositionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ContractOppositionDTO save(ContractOppositionDTO contractOppositionDTO) {
        log.debug("Request to save ContractOpposition : {}", contractOppositionDTO);
        ContractOpposition contractOpposition = contractOppositionMapper.toEntity(contractOppositionDTO);
        contractOpposition = contractOppositionRepository.save(contractOpposition);
        return contractOppositionMapper.toDto(contractOpposition);
    }

    /**
     * Get all the contractOppositions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContractOppositionDTO> findAll() {
        log.debug("Request to get all ContractOppositions");
        return contractOppositionRepository.findAll().stream()
            .map(contractOppositionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one contractOpposition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContractOppositionDTO> findOne(Long id) {
        log.debug("Request to get ContractOpposition : {}", id);
        return contractOppositionRepository.findById(id)
            .map(contractOppositionMapper::toDto);
    }

    /**
     * Delete the contractOpposition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContractOpposition : {}", id);
        contractOppositionRepository.deleteById(id);
    }
}
