package com.electron.mfs.pg.customer.service.impl;

import com.electron.mfs.pg.customer.service.CustomerLimitService;
import com.electron.mfs.pg.customer.domain.CustomerLimit;
import com.electron.mfs.pg.customer.repository.CustomerLimitRepository;
import com.electron.mfs.pg.customer.service.dto.CustomerLimitDTO;
import com.electron.mfs.pg.customer.service.mapper.CustomerLimitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerLimit}.
 */
@Service
@Transactional
public class CustomerLimitServiceImpl implements CustomerLimitService {

    private final Logger log = LoggerFactory.getLogger(CustomerLimitServiceImpl.class);

    private final CustomerLimitRepository customerLimitRepository;

    private final CustomerLimitMapper customerLimitMapper;

    public CustomerLimitServiceImpl(CustomerLimitRepository customerLimitRepository, CustomerLimitMapper customerLimitMapper) {
        this.customerLimitRepository = customerLimitRepository;
        this.customerLimitMapper = customerLimitMapper;
    }

    /**
     * Save a customerLimit.
     *
     * @param customerLimitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerLimitDTO save(CustomerLimitDTO customerLimitDTO) {
        log.debug("Request to save CustomerLimit : {}", customerLimitDTO);
        CustomerLimit customerLimit = customerLimitMapper.toEntity(customerLimitDTO);
        customerLimit = customerLimitRepository.save(customerLimit);
        return customerLimitMapper.toDto(customerLimit);
    }

    /**
     * Get all the customerLimits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerLimitDTO> findAll() {
        log.debug("Request to get all CustomerLimits");
        return customerLimitRepository.findAll().stream()
            .map(customerLimitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerLimit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerLimitDTO> findOne(Long id) {
        log.debug("Request to get CustomerLimit : {}", id);
        return customerLimitRepository.findById(id)
            .map(customerLimitMapper::toDto);
    }

    /**
     * Delete the customerLimit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerLimit : {}", id);
        customerLimitRepository.deleteById(id);
    }
}
