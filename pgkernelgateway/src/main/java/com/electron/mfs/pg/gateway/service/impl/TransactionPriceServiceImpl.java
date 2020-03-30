package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.TransactionPriceService;
import com.electron.mfs.pg.gateway.domain.TransactionPrice;
import com.electron.mfs.pg.gateway.repository.TransactionPriceRepository;
import com.electron.mfs.pg.gateway.service.dto.TransactionPriceDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionPrice}.
 */
@Service
@Transactional
public class TransactionPriceServiceImpl implements TransactionPriceService {

    private final Logger log = LoggerFactory.getLogger(TransactionPriceServiceImpl.class);

    private final TransactionPriceRepository transactionPriceRepository;

    private final TransactionPriceMapper transactionPriceMapper;

    public TransactionPriceServiceImpl(TransactionPriceRepository transactionPriceRepository, TransactionPriceMapper transactionPriceMapper) {
        this.transactionPriceRepository = transactionPriceRepository;
        this.transactionPriceMapper = transactionPriceMapper;
    }

    /**
     * Save a transactionPrice.
     *
     * @param transactionPriceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionPriceDTO save(TransactionPriceDTO transactionPriceDTO) {
        log.debug("Request to save TransactionPrice : {}", transactionPriceDTO);
        TransactionPrice transactionPrice = transactionPriceMapper.toEntity(transactionPriceDTO);
        transactionPrice = transactionPriceRepository.save(transactionPrice);
        return transactionPriceMapper.toDto(transactionPrice);
    }

    /**
     * Get all the transactionPrices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionPriceDTO> findAll() {
        log.debug("Request to get all TransactionPrices");
        return transactionPriceRepository.findAll().stream()
            .map(transactionPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionPriceDTO> findOne(Long id) {
        log.debug("Request to get TransactionPrice : {}", id);
        return transactionPriceRepository.findById(id)
            .map(transactionPriceMapper::toDto);
    }

    /**
     * Delete the transactionPrice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionPrice : {}", id);
        transactionPriceRepository.deleteById(id);
    }
}
