package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PriceCommissionService;
import com.electron.mfs.pg.gateway.domain.PriceCommission;
import com.electron.mfs.pg.gateway.repository.PriceCommissionRepository;
import com.electron.mfs.pg.gateway.service.dto.PriceCommissionDTO;
import com.electron.mfs.pg.gateway.service.mapper.PriceCommissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PriceCommission}.
 */
@Service
@Transactional
public class PriceCommissionServiceImpl implements PriceCommissionService {

    private final Logger log = LoggerFactory.getLogger(PriceCommissionServiceImpl.class);

    private final PriceCommissionRepository priceCommissionRepository;

    private final PriceCommissionMapper priceCommissionMapper;

    public PriceCommissionServiceImpl(PriceCommissionRepository priceCommissionRepository, PriceCommissionMapper priceCommissionMapper) {
        this.priceCommissionRepository = priceCommissionRepository;
        this.priceCommissionMapper = priceCommissionMapper;
    }

    /**
     * Save a priceCommission.
     *
     * @param priceCommissionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PriceCommissionDTO save(PriceCommissionDTO priceCommissionDTO) {
        log.debug("Request to save PriceCommission : {}", priceCommissionDTO);
        PriceCommission priceCommission = priceCommissionMapper.toEntity(priceCommissionDTO);
        priceCommission = priceCommissionRepository.save(priceCommission);
        return priceCommissionMapper.toDto(priceCommission);
    }

    /**
     * Get all the priceCommissions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriceCommissionDTO> findAll() {
        log.debug("Request to get all PriceCommissions");
        return priceCommissionRepository.findAll().stream()
            .map(priceCommissionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one priceCommission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriceCommissionDTO> findOne(Long id) {
        log.debug("Request to get PriceCommission : {}", id);
        return priceCommissionRepository.findById(id)
            .map(priceCommissionMapper::toDto);
    }

    /**
     * Delete the priceCommission by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceCommission : {}", id);
        priceCommissionRepository.deleteById(id);
    }
}
