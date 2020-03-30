package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.TransactionChannelService;
import com.electron.mfs.pg.mdm.domain.TransactionChannel;
import com.electron.mfs.pg.mdm.repository.TransactionChannelRepository;
import com.electron.mfs.pg.mdm.service.dto.TransactionChannelDTO;
import com.electron.mfs.pg.mdm.service.mapper.TransactionChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionChannel}.
 */
@Service
@Transactional
public class TransactionChannelServiceImpl implements TransactionChannelService {

    private final Logger log = LoggerFactory.getLogger(TransactionChannelServiceImpl.class);

    private final TransactionChannelRepository transactionChannelRepository;

    private final TransactionChannelMapper transactionChannelMapper;

    public TransactionChannelServiceImpl(TransactionChannelRepository transactionChannelRepository, TransactionChannelMapper transactionChannelMapper) {
        this.transactionChannelRepository = transactionChannelRepository;
        this.transactionChannelMapper = transactionChannelMapper;
    }

    /**
     * Save a transactionChannel.
     *
     * @param transactionChannelDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionChannelDTO save(TransactionChannelDTO transactionChannelDTO) {
        log.debug("Request to save TransactionChannel : {}", transactionChannelDTO);
        TransactionChannel transactionChannel = transactionChannelMapper.toEntity(transactionChannelDTO);
        transactionChannel = transactionChannelRepository.save(transactionChannel);
        return transactionChannelMapper.toDto(transactionChannel);
    }

    /**
     * Get all the transactionChannels.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionChannelDTO> findAll() {
        log.debug("Request to get all TransactionChannels");
        return transactionChannelRepository.findAll().stream()
            .map(transactionChannelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionChannel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionChannelDTO> findOne(Long id) {
        log.debug("Request to get TransactionChannel : {}", id);
        return transactionChannelRepository.findById(id)
            .map(transactionChannelMapper::toDto);
    }

    /**
     * Delete the transactionChannel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionChannel : {}", id);
        transactionChannelRepository.deleteById(id);
    }
}
