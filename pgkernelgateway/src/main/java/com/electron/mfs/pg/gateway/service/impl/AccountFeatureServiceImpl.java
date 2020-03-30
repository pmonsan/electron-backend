package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.AccountFeatureService;
import com.electron.mfs.pg.gateway.domain.AccountFeature;
import com.electron.mfs.pg.gateway.repository.AccountFeatureRepository;
import com.electron.mfs.pg.gateway.service.dto.AccountFeatureDTO;
import com.electron.mfs.pg.gateway.service.mapper.AccountFeatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AccountFeature}.
 */
@Service
@Transactional
public class AccountFeatureServiceImpl implements AccountFeatureService {

    private final Logger log = LoggerFactory.getLogger(AccountFeatureServiceImpl.class);

    private final AccountFeatureRepository accountFeatureRepository;

    private final AccountFeatureMapper accountFeatureMapper;

    public AccountFeatureServiceImpl(AccountFeatureRepository accountFeatureRepository, AccountFeatureMapper accountFeatureMapper) {
        this.accountFeatureRepository = accountFeatureRepository;
        this.accountFeatureMapper = accountFeatureMapper;
    }

    /**
     * Save a accountFeature.
     *
     * @param accountFeatureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountFeatureDTO save(AccountFeatureDTO accountFeatureDTO) {
        log.debug("Request to save AccountFeature : {}", accountFeatureDTO);
        AccountFeature accountFeature = accountFeatureMapper.toEntity(accountFeatureDTO);
        accountFeature = accountFeatureRepository.save(accountFeature);
        return accountFeatureMapper.toDto(accountFeature);
    }

    /**
     * Get all the accountFeatures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountFeatureDTO> findAll() {
        log.debug("Request to get all AccountFeatures");
        return accountFeatureRepository.findAll().stream()
            .map(accountFeatureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one accountFeature by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountFeatureDTO> findOne(Long id) {
        log.debug("Request to get AccountFeature : {}", id);
        return accountFeatureRepository.findById(id)
            .map(accountFeatureMapper::toDto);
    }

    /**
     * Delete the accountFeature by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountFeature : {}", id);
        accountFeatureRepository.deleteById(id);
    }
}
