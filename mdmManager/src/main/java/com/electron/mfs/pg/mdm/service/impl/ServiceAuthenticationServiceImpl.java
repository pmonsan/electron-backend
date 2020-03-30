package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.ServiceAuthenticationService;
import com.electron.mfs.pg.mdm.domain.ServiceAuthentication;
import com.electron.mfs.pg.mdm.repository.ServiceAuthenticationRepository;
import com.electron.mfs.pg.mdm.service.dto.ServiceAuthenticationDTO;
import com.electron.mfs.pg.mdm.service.mapper.ServiceAuthenticationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ServiceAuthentication}.
 */
@Service
@Transactional
public class ServiceAuthenticationServiceImpl implements ServiceAuthenticationService {

    private final Logger log = LoggerFactory.getLogger(ServiceAuthenticationServiceImpl.class);

    private final ServiceAuthenticationRepository serviceAuthenticationRepository;

    private final ServiceAuthenticationMapper serviceAuthenticationMapper;

    public ServiceAuthenticationServiceImpl(ServiceAuthenticationRepository serviceAuthenticationRepository, ServiceAuthenticationMapper serviceAuthenticationMapper) {
        this.serviceAuthenticationRepository = serviceAuthenticationRepository;
        this.serviceAuthenticationMapper = serviceAuthenticationMapper;
    }

    /**
     * Save a serviceAuthentication.
     *
     * @param serviceAuthenticationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceAuthenticationDTO save(ServiceAuthenticationDTO serviceAuthenticationDTO) {
        log.debug("Request to save ServiceAuthentication : {}", serviceAuthenticationDTO);
        ServiceAuthentication serviceAuthentication = serviceAuthenticationMapper.toEntity(serviceAuthenticationDTO);
        serviceAuthentication = serviceAuthenticationRepository.save(serviceAuthentication);
        return serviceAuthenticationMapper.toDto(serviceAuthentication);
    }

    /**
     * Get all the serviceAuthentications.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceAuthenticationDTO> findAll() {
        log.debug("Request to get all ServiceAuthentications");
        return serviceAuthenticationRepository.findAll().stream()
            .map(serviceAuthenticationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one serviceAuthentication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceAuthenticationDTO> findOne(Long id) {
        log.debug("Request to get ServiceAuthentication : {}", id);
        return serviceAuthenticationRepository.findById(id)
            .map(serviceAuthenticationMapper::toDto);
    }

    /**
     * Delete the serviceAuthentication by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceAuthentication : {}", id);
        serviceAuthenticationRepository.deleteById(id);
    }
}
