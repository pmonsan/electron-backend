package com.electron.mfs.pg.iam.service.impl;

import com.electron.mfs.pg.iam.service.UserConnectionService;
import com.electron.mfs.pg.iam.domain.UserConnection;
import com.electron.mfs.pg.iam.repository.UserConnectionRepository;
import com.electron.mfs.pg.iam.service.dto.UserConnectionDTO;
import com.electron.mfs.pg.iam.service.mapper.UserConnectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserConnection}.
 */
@Service
@Transactional
public class UserConnectionServiceImpl implements UserConnectionService {

    private final Logger log = LoggerFactory.getLogger(UserConnectionServiceImpl.class);

    private final UserConnectionRepository userConnectionRepository;

    private final UserConnectionMapper userConnectionMapper;

    public UserConnectionServiceImpl(UserConnectionRepository userConnectionRepository, UserConnectionMapper userConnectionMapper) {
        this.userConnectionRepository = userConnectionRepository;
        this.userConnectionMapper = userConnectionMapper;
    }

    /**
     * Save a userConnection.
     *
     * @param userConnectionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserConnectionDTO save(UserConnectionDTO userConnectionDTO) {
        log.debug("Request to save UserConnection : {}", userConnectionDTO);
        UserConnection userConnection = userConnectionMapper.toEntity(userConnectionDTO);
        userConnection = userConnectionRepository.save(userConnection);
        return userConnectionMapper.toDto(userConnection);
    }

    /**
     * Get all the userConnections.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserConnectionDTO> findAll() {
        log.debug("Request to get all UserConnections");
        return userConnectionRepository.findAll().stream()
            .map(userConnectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userConnection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserConnectionDTO> findOne(Long id) {
        log.debug("Request to get UserConnection : {}", id);
        return userConnectionRepository.findById(id)
            .map(userConnectionMapper::toDto);
    }

    /**
     * Delete the userConnection by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserConnection : {}", id);
        userConnectionRepository.deleteById(id);
    }
}
