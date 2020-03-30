package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.AccountStatusService;
import com.electron.mfs.pg.gateway.domain.AccountStatus;
import com.electron.mfs.pg.gateway.repository.AccountStatusRepository;
import com.electron.mfs.pg.gateway.service.dto.AccountStatusDTO;
import com.electron.mfs.pg.gateway.service.mapper.AccountStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AccountStatus}.
 */
@Service
@Transactional
public class AccountStatusServiceImpl implements AccountStatusService {

    private final Logger log = LoggerFactory.getLogger(AccountStatusServiceImpl.class);

    private final AccountStatusRepository accountStatusRepository;

    private final AccountStatusMapper accountStatusMapper;

    public AccountStatusServiceImpl(AccountStatusRepository accountStatusRepository, AccountStatusMapper accountStatusMapper) {
        this.accountStatusRepository = accountStatusRepository;
        this.accountStatusMapper = accountStatusMapper;
    }

    /**
     * Save a accountStatus.
     *
     * @param accountStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountStatusDTO save(AccountStatusDTO accountStatusDTO) {
        log.debug("Request to save AccountStatus : {}", accountStatusDTO);
        AccountStatus accountStatus = accountStatusMapper.toEntity(accountStatusDTO);
        accountStatus = accountStatusRepository.save(accountStatus);
        return accountStatusMapper.toDto(accountStatus);
    }

    /**
     * Get all the accountStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountStatusDTO> findAll() {
        log.debug("Request to get all AccountStatuses");
        return accountStatusRepository.findAll().stream()
            .map(accountStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one accountStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountStatusDTO> findOne(Long id) {
        log.debug("Request to get AccountStatus : {}", id);
        return accountStatusRepository.findById(id)
            .map(accountStatusMapper::toDto);
    }

    /**
     * Delete the accountStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountStatus : {}", id);
        accountStatusRepository.deleteById(id);
    }
}
