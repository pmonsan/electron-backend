package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.ServiceChannelService;
import com.electron.mfs.pg.gateway.domain.ServiceChannel;
import com.electron.mfs.pg.gateway.repository.ServiceChannelRepository;
import com.electron.mfs.pg.gateway.service.dto.ServiceChannelDTO;
import com.electron.mfs.pg.gateway.service.mapper.ServiceChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ServiceChannel}.
 */
@Service
@Transactional
public class ServiceChannelServiceImpl implements ServiceChannelService {

    private final Logger log = LoggerFactory.getLogger(ServiceChannelServiceImpl.class);

    private final ServiceChannelRepository serviceChannelRepository;

    private final ServiceChannelMapper serviceChannelMapper;

    public ServiceChannelServiceImpl(ServiceChannelRepository serviceChannelRepository, ServiceChannelMapper serviceChannelMapper) {
        this.serviceChannelRepository = serviceChannelRepository;
        this.serviceChannelMapper = serviceChannelMapper;
    }

    /**
     * Save a serviceChannel.
     *
     * @param serviceChannelDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceChannelDTO save(ServiceChannelDTO serviceChannelDTO) {
        log.debug("Request to save ServiceChannel : {}", serviceChannelDTO);
        ServiceChannel serviceChannel = serviceChannelMapper.toEntity(serviceChannelDTO);
        serviceChannel = serviceChannelRepository.save(serviceChannel);
        return serviceChannelMapper.toDto(serviceChannel);
    }

    /**
     * Get all the serviceChannels.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceChannelDTO> findAll() {
        log.debug("Request to get all ServiceChannels");
        return serviceChannelRepository.findAll().stream()
            .map(serviceChannelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one serviceChannel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceChannelDTO> findOne(Long id) {
        log.debug("Request to get ServiceChannel : {}", id);
        return serviceChannelRepository.findById(id)
            .map(serviceChannelMapper::toDto);
    }

    /**
     * Delete the serviceChannel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceChannel : {}", id);
        serviceChannelRepository.deleteById(id);
    }
}
