package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.TransactionStatusService;
import com.electron.mfs.pg.gateway.domain.TransactionStatus;
import com.electron.mfs.pg.gateway.repository.TransactionStatusRepository;
import com.electron.mfs.pg.gateway.service.dto.TransactionStatusDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionStatus}.
 */
@Service
@Transactional
public class TransactionStatusServiceImpl implements TransactionStatusService {

    private final Logger log = LoggerFactory.getLogger(TransactionStatusServiceImpl.class);

    private final TransactionStatusRepository transactionStatusRepository;

    private final TransactionStatusMapper transactionStatusMapper;

    public TransactionStatusServiceImpl(TransactionStatusRepository transactionStatusRepository, TransactionStatusMapper transactionStatusMapper) {
        this.transactionStatusRepository = transactionStatusRepository;
        this.transactionStatusMapper = transactionStatusMapper;
    }

    /**
     * Save a transactionStatus.
     *
     * @param transactionStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionStatusDTO save(TransactionStatusDTO transactionStatusDTO) {
        log.debug("Request to save TransactionStatus : {}", transactionStatusDTO);
        TransactionStatus transactionStatus = transactionStatusMapper.toEntity(transactionStatusDTO);
        transactionStatus = transactionStatusRepository.save(transactionStatus);
        return transactionStatusMapper.toDto(transactionStatus);
    }

    /**
     * Get all the transactionStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionStatusDTO> findAll() {
        log.debug("Request to get all TransactionStatuses");
        return transactionStatusRepository.findAll().stream()
            .map(transactionStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionStatusDTO> findOne(Long id) {
        log.debug("Request to get TransactionStatus : {}", id);
        return transactionStatusRepository.findById(id)
            .map(transactionStatusMapper::toDto);
    }

    /**
     * Delete the transactionStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionStatus : {}", id);
        transactionStatusRepository.deleteById(id);
    }
}
