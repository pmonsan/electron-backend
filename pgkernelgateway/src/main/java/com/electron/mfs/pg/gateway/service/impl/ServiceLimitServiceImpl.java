package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.ServiceLimitService;
import com.electron.mfs.pg.gateway.domain.ServiceLimit;
import com.electron.mfs.pg.gateway.repository.ServiceLimitRepository;
import com.electron.mfs.pg.gateway.service.dto.ServiceLimitDTO;
import com.electron.mfs.pg.gateway.service.mapper.ServiceLimitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ServiceLimit}.
 */
@Service
@Transactional
public class ServiceLimitServiceImpl implements ServiceLimitService {

    private final Logger log = LoggerFactory.getLogger(ServiceLimitServiceImpl.class);

    private final ServiceLimitRepository serviceLimitRepository;

    private final ServiceLimitMapper serviceLimitMapper;

    public ServiceLimitServiceImpl(ServiceLimitRepository serviceLimitRepository, ServiceLimitMapper serviceLimitMapper) {
        this.serviceLimitRepository = serviceLimitRepository;
        this.serviceLimitMapper = serviceLimitMapper;
    }

    /**
     * Save a serviceLimit.
     *
     * @param serviceLimitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceLimitDTO save(ServiceLimitDTO serviceLimitDTO) {
        log.debug("Request to save ServiceLimit : {}", serviceLimitDTO);
        ServiceLimit serviceLimit = serviceLimitMapper.toEntity(serviceLimitDTO);
        serviceLimit = serviceLimitRepository.save(serviceLimit);
        return serviceLimitMapper.toDto(serviceLimit);
    }

    /**
     * Get all the serviceLimits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceLimitDTO> findAll() {
        log.debug("Request to get all ServiceLimits");
        return serviceLimitRepository.findAll().stream()
            .map(serviceLimitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one serviceLimit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceLimitDTO> findOne(Long id) {
        log.debug("Request to get ServiceLimit : {}", id);
        return serviceLimitRepository.findById(id)
            .map(serviceLimitMapper::toDto);
    }

    /**
     * Delete the serviceLimit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceLimit : {}", id);
        serviceLimitRepository.deleteById(id);
    }
}
