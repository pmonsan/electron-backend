package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PricePlanService;
import com.electron.mfs.pg.gateway.domain.PricePlan;
import com.electron.mfs.pg.gateway.repository.PricePlanRepository;
import com.electron.mfs.pg.gateway.service.dto.PricePlanDTO;
import com.electron.mfs.pg.gateway.service.mapper.PricePlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PricePlan}.
 */
@Service
@Transactional
public class PricePlanServiceImpl implements PricePlanService {

    private final Logger log = LoggerFactory.getLogger(PricePlanServiceImpl.class);

    private final PricePlanRepository pricePlanRepository;

    private final PricePlanMapper pricePlanMapper;

    public PricePlanServiceImpl(PricePlanRepository pricePlanRepository, PricePlanMapper pricePlanMapper) {
        this.pricePlanRepository = pricePlanRepository;
        this.pricePlanMapper = pricePlanMapper;
    }

    /**
     * Save a pricePlan.
     *
     * @param pricePlanDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PricePlanDTO save(PricePlanDTO pricePlanDTO) {
        log.debug("Request to save PricePlan : {}", pricePlanDTO);
        PricePlan pricePlan = pricePlanMapper.toEntity(pricePlanDTO);
        pricePlan = pricePlanRepository.save(pricePlan);
        return pricePlanMapper.toDto(pricePlan);
    }

    /**
     * Get all the pricePlans.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PricePlanDTO> findAll() {
        log.debug("Request to get all PricePlans");
        return pricePlanRepository.findAll().stream()
            .map(pricePlanMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pricePlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PricePlanDTO> findOne(Long id) {
        log.debug("Request to get PricePlan : {}", id);
        return pricePlanRepository.findById(id)
            .map(pricePlanMapper::toDto);
    }

    /**
     * Delete the pricePlan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PricePlan : {}", id);
        pricePlanRepository.deleteById(id);
    }
}
