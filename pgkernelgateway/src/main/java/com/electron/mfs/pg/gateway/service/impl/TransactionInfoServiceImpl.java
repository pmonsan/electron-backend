package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.TransactionInfoService;
import com.electron.mfs.pg.gateway.domain.TransactionInfo;
import com.electron.mfs.pg.gateway.repository.TransactionInfoRepository;
import com.electron.mfs.pg.gateway.service.dto.TransactionInfoDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionInfo}.
 */
@Service
@Transactional
public class TransactionInfoServiceImpl implements TransactionInfoService {

    private final Logger log = LoggerFactory.getLogger(TransactionInfoServiceImpl.class);

    private final TransactionInfoRepository transactionInfoRepository;

    private final TransactionInfoMapper transactionInfoMapper;

    public TransactionInfoServiceImpl(TransactionInfoRepository transactionInfoRepository, TransactionInfoMapper transactionInfoMapper) {
        this.transactionInfoRepository = transactionInfoRepository;
        this.transactionInfoMapper = transactionInfoMapper;
    }

    /**
     * Save a transactionInfo.
     *
     * @param transactionInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionInfoDTO save(TransactionInfoDTO transactionInfoDTO) {
        log.debug("Request to save TransactionInfo : {}", transactionInfoDTO);
        TransactionInfo transactionInfo = transactionInfoMapper.toEntity(transactionInfoDTO);
        transactionInfo = transactionInfoRepository.save(transactionInfo);
        return transactionInfoMapper.toDto(transactionInfo);
    }

    /**
     * Get all the transactionInfos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionInfoDTO> findAll() {
        log.debug("Request to get all TransactionInfos");
        return transactionInfoRepository.findAll().stream()
            .map(transactionInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionInfoDTO> findOne(Long id) {
        log.debug("Request to get TransactionInfo : {}", id);
        return transactionInfoRepository.findById(id)
            .map(transactionInfoMapper::toDto);
    }

    /**
     * Delete the transactionInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionInfo : {}", id);
        transactionInfoRepository.deleteById(id);
    }
}
