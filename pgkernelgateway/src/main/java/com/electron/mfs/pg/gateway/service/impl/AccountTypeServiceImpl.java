package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.AccountTypeService;
import com.electron.mfs.pg.gateway.domain.AccountType;
import com.electron.mfs.pg.gateway.repository.AccountTypeRepository;
import com.electron.mfs.pg.gateway.service.dto.AccountTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.AccountTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AccountType}.
 */
@Service
@Transactional
public class AccountTypeServiceImpl implements AccountTypeService {

    private final Logger log = LoggerFactory.getLogger(AccountTypeServiceImpl.class);

    private final AccountTypeRepository accountTypeRepository;

    private final AccountTypeMapper accountTypeMapper;

    public AccountTypeServiceImpl(AccountTypeRepository accountTypeRepository, AccountTypeMapper accountTypeMapper) {
        this.accountTypeRepository = accountTypeRepository;
        this.accountTypeMapper = accountTypeMapper;
    }

    /**
     * Save a accountType.
     *
     * @param accountTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountTypeDTO save(AccountTypeDTO accountTypeDTO) {
        log.debug("Request to save AccountType : {}", accountTypeDTO);
        AccountType accountType = accountTypeMapper.toEntity(accountTypeDTO);
        accountType = accountTypeRepository.save(accountType);
        return accountTypeMapper.toDto(accountType);
    }

    /**
     * Get all the accountTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountTypeDTO> findAll() {
        log.debug("Request to get all AccountTypes");
        return accountTypeRepository.findAll().stream()
            .map(accountTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one accountType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountTypeDTO> findOne(Long id) {
        log.debug("Request to get AccountType : {}", id);
        return accountTypeRepository.findById(id)
            .map(accountTypeMapper::toDto);
    }

    /**
     * Delete the accountType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountType : {}", id);
        accountTypeRepository.deleteById(id);
    }
}
