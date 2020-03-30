package com.electron.mfs.pg.transactions.service.impl;

import com.electron.mfs.pg.transactions.service.TransactionCommissionService;
import com.electron.mfs.pg.transactions.domain.TransactionCommission;
import com.electron.mfs.pg.transactions.repository.TransactionCommissionRepository;
import com.electron.mfs.pg.transactions.service.dto.TransactionCommissionDTO;
import com.electron.mfs.pg.transactions.service.mapper.TransactionCommissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionCommission}.
 */
@Service
@Transactional
public class TransactionCommissionServiceImpl implements TransactionCommissionService {

    private final Logger log = LoggerFactory.getLogger(TransactionCommissionServiceImpl.class);

    private final TransactionCommissionRepository transactionCommissionRepository;

    private final TransactionCommissionMapper transactionCommissionMapper;

    public TransactionCommissionServiceImpl(TransactionCommissionRepository transactionCommissionRepository, TransactionCommissionMapper transactionCommissionMapper) {
        this.transactionCommissionRepository = transactionCommissionRepository;
        this.transactionCommissionMapper = transactionCommissionMapper;
    }

    /**
     * Save a transactionCommission.
     *
     * @param transactionCommissionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionCommissionDTO save(TransactionCommissionDTO transactionCommissionDTO) {
        log.debug("Request to save TransactionCommission : {}", transactionCommissionDTO);
        TransactionCommission transactionCommission = transactionCommissionMapper.toEntity(transactionCommissionDTO);
        transactionCommission = transactionCommissionRepository.save(transactionCommission);
        return transactionCommissionMapper.toDto(transactionCommission);
    }

    /**
     * Get all the transactionCommissions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionCommissionDTO> findAll() {
        log.debug("Request to get all TransactionCommissions");
        return transactionCommissionRepository.findAll().stream()
            .map(transactionCommissionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionCommission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionCommissionDTO> findOne(Long id) {
        log.debug("Request to get TransactionCommission : {}", id);
        return transactionCommissionRepository.findById(id)
            .map(transactionCommissionMapper::toDto);
    }

    /**
     * Delete the transactionCommission by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionCommission : {}", id);
        transactionCommissionRepository.deleteById(id);
    }
}
