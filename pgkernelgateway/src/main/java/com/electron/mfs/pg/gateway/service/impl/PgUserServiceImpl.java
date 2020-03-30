package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgUserService;
import com.electron.mfs.pg.gateway.domain.PgUser;
import com.electron.mfs.pg.gateway.repository.PgUserRepository;
import com.electron.mfs.pg.gateway.service.dto.PgUserDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgUser}.
 */
@Service
@Transactional
public class PgUserServiceImpl implements PgUserService {

    private final Logger log = LoggerFactory.getLogger(PgUserServiceImpl.class);

    private final PgUserRepository pgUserRepository;

    private final PgUserMapper pgUserMapper;

    public PgUserServiceImpl(PgUserRepository pgUserRepository, PgUserMapper pgUserMapper) {
        this.pgUserRepository = pgUserRepository;
        this.pgUserMapper = pgUserMapper;
    }

    /**
     * Save a pgUser.
     *
     * @param pgUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgUserDTO save(PgUserDTO pgUserDTO) {
        log.debug("Request to save PgUser : {}", pgUserDTO);
        PgUser pgUser = pgUserMapper.toEntity(pgUserDTO);
        pgUser = pgUserRepository.save(pgUser);
        return pgUserMapper.toDto(pgUser);
    }

    /**
     * Get all the pgUsers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgUserDTO> findAll() {
        log.debug("Request to get all PgUsers");
        return pgUserRepository.findAll().stream()
            .map(pgUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgUserDTO> findOne(Long id) {
        log.debug("Request to get PgUser : {}", id);
        return pgUserRepository.findById(id)
            .map(pgUserMapper::toDto);
    }

    /**
     * Delete the pgUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgUser : {}", id);
        pgUserRepository.deleteById(id);
    }
}
