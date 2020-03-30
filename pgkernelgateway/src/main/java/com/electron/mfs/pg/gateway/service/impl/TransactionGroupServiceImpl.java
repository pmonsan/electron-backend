package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.TransactionGroupService;
import com.electron.mfs.pg.gateway.domain.TransactionGroup;
import com.electron.mfs.pg.gateway.repository.TransactionGroupRepository;
import com.electron.mfs.pg.gateway.service.dto.TransactionGroupDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionGroup}.
 */
@Service
@Transactional
public class TransactionGroupServiceImpl implements TransactionGroupService {

    private final Logger log = LoggerFactory.getLogger(TransactionGroupServiceImpl.class);

    private final TransactionGroupRepository transactionGroupRepository;

    private final TransactionGroupMapper transactionGroupMapper;

    public TransactionGroupServiceImpl(TransactionGroupRepository transactionGroupRepository, TransactionGroupMapper transactionGroupMapper) {
        this.transactionGroupRepository = transactionGroupRepository;
        this.transactionGroupMapper = transactionGroupMapper;
    }

    /**
     * Save a transactionGroup.
     *
     * @param transactionGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionGroupDTO save(TransactionGroupDTO transactionGroupDTO) {
        log.debug("Request to save TransactionGroup : {}", transactionGroupDTO);
        TransactionGroup transactionGroup = transactionGroupMapper.toEntity(transactionGroupDTO);
        transactionGroup = transactionGroupRepository.save(transactionGroup);
        return transactionGroupMapper.toDto(transactionGroup);
    }

    /**
     * Get all the transactionGroups.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionGroupDTO> findAll() {
        log.debug("Request to get all TransactionGroups");
        return transactionGroupRepository.findAll().stream()
            .map(transactionGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionGroupDTO> findOne(Long id) {
        log.debug("Request to get TransactionGroup : {}", id);
        return transactionGroupRepository.findById(id)
            .map(transactionGroupMapper::toDto);
    }

    /**
     * Delete the transactionGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionGroup : {}", id);
        transactionGroupRepository.deleteById(id);
    }
}
