package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.CustomerBlacklistService;
import com.electron.mfs.pg.gateway.domain.CustomerBlacklist;
import com.electron.mfs.pg.gateway.repository.CustomerBlacklistRepository;
import com.electron.mfs.pg.gateway.service.dto.CustomerBlacklistDTO;
import com.electron.mfs.pg.gateway.service.mapper.CustomerBlacklistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerBlacklist}.
 */
@Service
@Transactional
public class CustomerBlacklistServiceImpl implements CustomerBlacklistService {

    private final Logger log = LoggerFactory.getLogger(CustomerBlacklistServiceImpl.class);

    private final CustomerBlacklistRepository customerBlacklistRepository;

    private final CustomerBlacklistMapper customerBlacklistMapper;

    public CustomerBlacklistServiceImpl(CustomerBlacklistRepository customerBlacklistRepository, CustomerBlacklistMapper customerBlacklistMapper) {
        this.customerBlacklistRepository = customerBlacklistRepository;
        this.customerBlacklistMapper = customerBlacklistMapper;
    }

    /**
     * Save a customerBlacklist.
     *
     * @param customerBlacklistDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerBlacklistDTO save(CustomerBlacklistDTO customerBlacklistDTO) {
        log.debug("Request to save CustomerBlacklist : {}", customerBlacklistDTO);
        CustomerBlacklist customerBlacklist = customerBlacklistMapper.toEntity(customerBlacklistDTO);
        customerBlacklist = customerBlacklistRepository.save(customerBlacklist);
        return customerBlacklistMapper.toDto(customerBlacklist);
    }

    /**
     * Get all the customerBlacklists.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerBlacklistDTO> findAll() {
        log.debug("Request to get all CustomerBlacklists");
        return customerBlacklistRepository.findAll().stream()
            .map(customerBlacklistMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerBlacklist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerBlacklistDTO> findOne(Long id) {
        log.debug("Request to get CustomerBlacklist : {}", id);
        return customerBlacklistRepository.findById(id)
            .map(customerBlacklistMapper::toDto);
    }

    /**
     * Delete the customerBlacklist by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerBlacklist : {}", id);
        customerBlacklistRepository.deleteById(id);
    }
}
