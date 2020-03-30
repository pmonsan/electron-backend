package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.TransactionPropertyService;
import com.electron.mfs.pg.mdm.domain.TransactionProperty;
import com.electron.mfs.pg.mdm.repository.TransactionPropertyRepository;
import com.electron.mfs.pg.mdm.service.dto.TransactionPropertyDTO;
import com.electron.mfs.pg.mdm.service.mapper.TransactionPropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionProperty}.
 */
@Service
@Transactional
public class TransactionPropertyServiceImpl implements TransactionPropertyService {

    private final Logger log = LoggerFactory.getLogger(TransactionPropertyServiceImpl.class);

    private final TransactionPropertyRepository transactionPropertyRepository;

    private final TransactionPropertyMapper transactionPropertyMapper;

    public TransactionPropertyServiceImpl(TransactionPropertyRepository transactionPropertyRepository, TransactionPropertyMapper transactionPropertyMapper) {
        this.transactionPropertyRepository = transactionPropertyRepository;
        this.transactionPropertyMapper = transactionPropertyMapper;
    }

    /**
     * Save a transactionProperty.
     *
     * @param transactionPropertyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionPropertyDTO save(TransactionPropertyDTO transactionPropertyDTO) {
        log.debug("Request to save TransactionProperty : {}", transactionPropertyDTO);
        TransactionProperty transactionProperty = transactionPropertyMapper.toEntity(transactionPropertyDTO);
        transactionProperty = transactionPropertyRepository.save(transactionProperty);
        return transactionPropertyMapper.toDto(transactionProperty);
    }

    /**
     * Get all the transactionProperties.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionPropertyDTO> findAll() {
        log.debug("Request to get all TransactionProperties");
        return transactionPropertyRepository.findAll().stream()
            .map(transactionPropertyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionProperty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionPropertyDTO> findOne(Long id) {
        log.debug("Request to get TransactionProperty : {}", id);
        return transactionPropertyRepository.findById(id)
            .map(transactionPropertyMapper::toDto);
    }

    /**
     * Delete the transactionProperty by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionProperty : {}", id);
        transactionPropertyRepository.deleteById(id);
    }
}
