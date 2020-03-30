package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.SubscriptionPriceService;
import com.electron.mfs.pg.gateway.domain.SubscriptionPrice;
import com.electron.mfs.pg.gateway.repository.SubscriptionPriceRepository;
import com.electron.mfs.pg.gateway.service.dto.SubscriptionPriceDTO;
import com.electron.mfs.pg.gateway.service.mapper.SubscriptionPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SubscriptionPrice}.
 */
@Service
@Transactional
public class SubscriptionPriceServiceImpl implements SubscriptionPriceService {

    private final Logger log = LoggerFactory.getLogger(SubscriptionPriceServiceImpl.class);

    private final SubscriptionPriceRepository subscriptionPriceRepository;

    private final SubscriptionPriceMapper subscriptionPriceMapper;

    public SubscriptionPriceServiceImpl(SubscriptionPriceRepository subscriptionPriceRepository, SubscriptionPriceMapper subscriptionPriceMapper) {
        this.subscriptionPriceRepository = subscriptionPriceRepository;
        this.subscriptionPriceMapper = subscriptionPriceMapper;
    }

    /**
     * Save a subscriptionPrice.
     *
     * @param subscriptionPriceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubscriptionPriceDTO save(SubscriptionPriceDTO subscriptionPriceDTO) {
        log.debug("Request to save SubscriptionPrice : {}", subscriptionPriceDTO);
        SubscriptionPrice subscriptionPrice = subscriptionPriceMapper.toEntity(subscriptionPriceDTO);
        subscriptionPrice = subscriptionPriceRepository.save(subscriptionPrice);
        return subscriptionPriceMapper.toDto(subscriptionPrice);
    }

    /**
     * Get all the subscriptionPrices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionPriceDTO> findAll() {
        log.debug("Request to get all SubscriptionPrices");
        return subscriptionPriceRepository.findAll().stream()
            .map(subscriptionPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one subscriptionPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubscriptionPriceDTO> findOne(Long id) {
        log.debug("Request to get SubscriptionPrice : {}", id);
        return subscriptionPriceRepository.findById(id)
            .map(subscriptionPriceMapper::toDto);
    }

    /**
     * Delete the subscriptionPrice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubscriptionPrice : {}", id);
        subscriptionPriceRepository.deleteById(id);
    }
}
