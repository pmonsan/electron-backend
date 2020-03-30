package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.TransactionTypeService;
import com.electron.mfs.pg.gateway.domain.TransactionType;
import com.electron.mfs.pg.gateway.repository.TransactionTypeRepository;
import com.electron.mfs.pg.gateway.service.dto.TransactionTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionType}.
 */
@Service
@Transactional
public class TransactionTypeServiceImpl implements TransactionTypeService {

    private final Logger log = LoggerFactory.getLogger(TransactionTypeServiceImpl.class);

    private final TransactionTypeRepository transactionTypeRepository;

    private final TransactionTypeMapper transactionTypeMapper;

    public TransactionTypeServiceImpl(TransactionTypeRepository transactionTypeRepository, TransactionTypeMapper transactionTypeMapper) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionTypeMapper = transactionTypeMapper;
    }

    /**
     * Save a transactionType.
     *
     * @param transactionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionTypeDTO save(TransactionTypeDTO transactionTypeDTO) {
        log.debug("Request to save TransactionType : {}", transactionTypeDTO);
        TransactionType transactionType = transactionTypeMapper.toEntity(transactionTypeDTO);
        transactionType = transactionTypeRepository.save(transactionType);
        return transactionTypeMapper.toDto(transactionType);
    }

    /**
     * Get all the transactionTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionTypeDTO> findAll() {
        log.debug("Request to get all TransactionTypes");
        return transactionTypeRepository.findAll().stream()
            .map(transactionTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionTypeDTO> findOne(Long id) {
        log.debug("Request to get TransactionType : {}", id);
        return transactionTypeRepository.findById(id)
            .map(transactionTypeMapper::toDto);
    }

    /**
     * Delete the transactionType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionType : {}", id);
        transactionTypeRepository.deleteById(id);
    }
}
