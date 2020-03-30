package com.electron.mfs.pg.transactions.service.impl;

import com.electron.mfs.pg.transactions.service.DetailTransactionService;
import com.electron.mfs.pg.transactions.domain.DetailTransaction;
import com.electron.mfs.pg.transactions.repository.DetailTransactionRepository;
import com.electron.mfs.pg.transactions.service.dto.DetailTransactionDTO;
import com.electron.mfs.pg.transactions.service.mapper.DetailTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DetailTransaction}.
 */
@Service
@Transactional
public class DetailTransactionServiceImpl implements DetailTransactionService {

    private final Logger log = LoggerFactory.getLogger(DetailTransactionServiceImpl.class);

    private final DetailTransactionRepository detailTransactionRepository;

    private final DetailTransactionMapper detailTransactionMapper;

    public DetailTransactionServiceImpl(DetailTransactionRepository detailTransactionRepository, DetailTransactionMapper detailTransactionMapper) {
        this.detailTransactionRepository = detailTransactionRepository;
        this.detailTransactionMapper = detailTransactionMapper;
    }

    /**
     * Save a detailTransaction.
     *
     * @param detailTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DetailTransactionDTO save(DetailTransactionDTO detailTransactionDTO) {
        log.debug("Request to save DetailTransaction : {}", detailTransactionDTO);
        DetailTransaction detailTransaction = detailTransactionMapper.toEntity(detailTransactionDTO);
        detailTransaction = detailTransactionRepository.save(detailTransaction);
        return detailTransactionMapper.toDto(detailTransaction);
    }

    /**
     * Get all the detailTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DetailTransactionDTO> findAll() {
        log.debug("Request to get all DetailTransactions");
        return detailTransactionRepository.findAll().stream()
            .map(detailTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one detailTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetailTransactionDTO> findOne(Long id) {
        log.debug("Request to get DetailTransaction : {}", id);
        return detailTransactionRepository.findById(id)
            .map(detailTransactionMapper::toDto);
    }

    /**
     * Delete the detailTransaction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailTransaction : {}", id);
        detailTransactionRepository.deleteById(id);
    }
}
