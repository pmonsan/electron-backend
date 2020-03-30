package com.electron.mfs.pg.feescommission.service.impl;

import com.electron.mfs.pg.feescommission.service.PriceService;
import com.electron.mfs.pg.feescommission.domain.Price;
import com.electron.mfs.pg.feescommission.repository.PriceRepository;
import com.electron.mfs.pg.feescommission.service.dto.PriceDTO;
import com.electron.mfs.pg.feescommission.service.mapper.PriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Price}.
 */
@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final PriceRepository priceRepository;

    private final PriceMapper priceMapper;

    public PriceServiceImpl(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    /**
     * Save a price.
     *
     * @param priceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PriceDTO save(PriceDTO priceDTO) {
        log.debug("Request to save Price : {}", priceDTO);
        Price price = priceMapper.toEntity(priceDTO);
        price = priceRepository.save(price);
        return priceMapper.toDto(price);
    }

    /**
     * Get all the prices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriceDTO> findAll() {
        log.debug("Request to get all Prices");
        return priceRepository.findAll().stream()
            .map(priceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one price by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriceDTO> findOne(Long id) {
        log.debug("Request to get Price : {}", id);
        return priceRepository.findById(id)
            .map(priceMapper::toDto);
    }

    /**
     * Delete the price by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Price : {}", id);
        priceRepository.deleteById(id);
    }
}
