package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.CustomerSubscriptionService;
import com.electron.mfs.pg.gateway.domain.CustomerSubscription;
import com.electron.mfs.pg.gateway.repository.CustomerSubscriptionRepository;
import com.electron.mfs.pg.gateway.service.dto.CustomerSubscriptionDTO;
import com.electron.mfs.pg.gateway.service.mapper.CustomerSubscriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerSubscription}.
 */
@Service
@Transactional
public class CustomerSubscriptionServiceImpl implements CustomerSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(CustomerSubscriptionServiceImpl.class);

    private final CustomerSubscriptionRepository customerSubscriptionRepository;

    private final CustomerSubscriptionMapper customerSubscriptionMapper;

    public CustomerSubscriptionServiceImpl(CustomerSubscriptionRepository customerSubscriptionRepository, CustomerSubscriptionMapper customerSubscriptionMapper) {
        this.customerSubscriptionRepository = customerSubscriptionRepository;
        this.customerSubscriptionMapper = customerSubscriptionMapper;
    }

    /**
     * Save a customerSubscription.
     *
     * @param customerSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerSubscriptionDTO save(CustomerSubscriptionDTO customerSubscriptionDTO) {
        log.debug("Request to save CustomerSubscription : {}", customerSubscriptionDTO);
        CustomerSubscription customerSubscription = customerSubscriptionMapper.toEntity(customerSubscriptionDTO);
        customerSubscription = customerSubscriptionRepository.save(customerSubscription);
        return customerSubscriptionMapper.toDto(customerSubscription);
    }

    /**
     * Get all the customerSubscriptions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerSubscriptionDTO> findAll() {
        log.debug("Request to get all CustomerSubscriptions");
        return customerSubscriptionRepository.findAll().stream()
            .map(customerSubscriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerSubscription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerSubscriptionDTO> findOne(Long id) {
        log.debug("Request to get CustomerSubscription : {}", id);
        return customerSubscriptionRepository.findById(id)
            .map(customerSubscriptionMapper::toDto);
    }

    /**
     * Delete the customerSubscription by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerSubscription : {}", id);
        customerSubscriptionRepository.deleteById(id);
    }
}
