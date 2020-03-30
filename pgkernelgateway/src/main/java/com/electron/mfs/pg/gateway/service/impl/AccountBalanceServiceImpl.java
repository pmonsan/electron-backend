package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.AccountBalanceService;
import com.electron.mfs.pg.gateway.domain.AccountBalance;
import com.electron.mfs.pg.gateway.repository.AccountBalanceRepository;
import com.electron.mfs.pg.gateway.service.dto.AccountBalanceDTO;
import com.electron.mfs.pg.gateway.service.mapper.AccountBalanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AccountBalance}.
 */
@Service
@Transactional
public class AccountBalanceServiceImpl implements AccountBalanceService {

    private final Logger log = LoggerFactory.getLogger(AccountBalanceServiceImpl.class);

    private final AccountBalanceRepository accountBalanceRepository;

    private final AccountBalanceMapper accountBalanceMapper;

    public AccountBalanceServiceImpl(AccountBalanceRepository accountBalanceRepository, AccountBalanceMapper accountBalanceMapper) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.accountBalanceMapper = accountBalanceMapper;
    }

    /**
     * Save a accountBalance.
     *
     * @param accountBalanceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountBalanceDTO save(AccountBalanceDTO accountBalanceDTO) {
        log.debug("Request to save AccountBalance : {}", accountBalanceDTO);
        AccountBalance accountBalance = accountBalanceMapper.toEntity(accountBalanceDTO);
        accountBalance = accountBalanceRepository.save(accountBalance);
        return accountBalanceMapper.toDto(accountBalance);
    }

    /**
     * Get all the accountBalances.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountBalanceDTO> findAll() {
        log.debug("Request to get all AccountBalances");
        return accountBalanceRepository.findAll().stream()
            .map(accountBalanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one accountBalance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountBalanceDTO> findOne(Long id) {
        log.debug("Request to get AccountBalance : {}", id);
        return accountBalanceRepository.findById(id)
            .map(accountBalanceMapper::toDto);
    }

    /**
     * Delete the accountBalance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountBalance : {}", id);
        accountBalanceRepository.deleteById(id);
    }
}
