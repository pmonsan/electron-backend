package com.electron.mfs.pg.account.service.impl;

import com.electron.mfs.pg.account.service.PgAccountService;
import com.electron.mfs.pg.account.domain.PgAccount;
import com.electron.mfs.pg.account.repository.PgAccountRepository;
import com.electron.mfs.pg.account.service.dto.PgAccountDTO;
import com.electron.mfs.pg.account.service.mapper.PgAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgAccount}.
 */
@Service
@Transactional
public class PgAccountServiceImpl implements PgAccountService {

    private final Logger log = LoggerFactory.getLogger(PgAccountServiceImpl.class);

    private final PgAccountRepository pgAccountRepository;

    private final PgAccountMapper pgAccountMapper;

    public PgAccountServiceImpl(PgAccountRepository pgAccountRepository, PgAccountMapper pgAccountMapper) {
        this.pgAccountRepository = pgAccountRepository;
        this.pgAccountMapper = pgAccountMapper;
    }

    /**
     * Save a pgAccount.
     *
     * @param pgAccountDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgAccountDTO save(PgAccountDTO pgAccountDTO) {
        log.debug("Request to save PgAccount : {}", pgAccountDTO);
        PgAccount pgAccount = pgAccountMapper.toEntity(pgAccountDTO);
        pgAccount = pgAccountRepository.save(pgAccount);
        return pgAccountMapper.toDto(pgAccount);
    }

    /**
     * Get all the pgAccounts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgAccountDTO> findAll() {
        log.debug("Request to get all PgAccounts");
        return pgAccountRepository.findAll().stream()
            .map(pgAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgAccountDTO> findOne(Long id) {
        log.debug("Request to get PgAccount : {}", id);
        return pgAccountRepository.findById(id)
            .map(pgAccountMapper::toDto);
    }

    /**
     * Delete the pgAccount by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgAccount : {}", id);
        pgAccountRepository.deleteById(id);
    }
}
