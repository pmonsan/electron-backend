package com.electron.mfs.pg.subscription.service.impl;

import com.electron.mfs.pg.subscription.service.ServiceIntegrationService;
import com.electron.mfs.pg.subscription.domain.ServiceIntegration;
import com.electron.mfs.pg.subscription.repository.ServiceIntegrationRepository;
import com.electron.mfs.pg.subscription.service.dto.ServiceIntegrationDTO;
import com.electron.mfs.pg.subscription.service.mapper.ServiceIntegrationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ServiceIntegration}.
 */
@Service
@Transactional
public class ServiceIntegrationServiceImpl implements ServiceIntegrationService {

    private final Logger log = LoggerFactory.getLogger(ServiceIntegrationServiceImpl.class);

    private final ServiceIntegrationRepository serviceIntegrationRepository;

    private final ServiceIntegrationMapper serviceIntegrationMapper;

    public ServiceIntegrationServiceImpl(ServiceIntegrationRepository serviceIntegrationRepository, ServiceIntegrationMapper serviceIntegrationMapper) {
        this.serviceIntegrationRepository = serviceIntegrationRepository;
        this.serviceIntegrationMapper = serviceIntegrationMapper;
    }

    /**
     * Save a serviceIntegration.
     *
     * @param serviceIntegrationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceIntegrationDTO save(ServiceIntegrationDTO serviceIntegrationDTO) {
        log.debug("Request to save ServiceIntegration : {}", serviceIntegrationDTO);
        ServiceIntegration serviceIntegration = serviceIntegrationMapper.toEntity(serviceIntegrationDTO);
        serviceIntegration = serviceIntegrationRepository.save(serviceIntegration);
        return serviceIntegrationMapper.toDto(serviceIntegration);
    }

    /**
     * Get all the serviceIntegrations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceIntegrationDTO> findAll() {
        log.debug("Request to get all ServiceIntegrations");
        return serviceIntegrationRepository.findAll().stream()
            .map(serviceIntegrationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one serviceIntegration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceIntegrationDTO> findOne(Long id) {
        log.debug("Request to get ServiceIntegration : {}", id);
        return serviceIntegrationRepository.findById(id)
            .map(serviceIntegrationMapper::toDto);
    }

    /**
     * Delete the serviceIntegration by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceIntegration : {}", id);
        serviceIntegrationRepository.deleteById(id);
    }
}
