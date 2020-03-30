package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.UserProfileDataService;
import com.electron.mfs.pg.gateway.domain.UserProfileData;
import com.electron.mfs.pg.gateway.repository.UserProfileDataRepository;
import com.electron.mfs.pg.gateway.service.dto.UserProfileDataDTO;
import com.electron.mfs.pg.gateway.service.mapper.UserProfileDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserProfileData}.
 */
@Service
@Transactional
public class UserProfileDataServiceImpl implements UserProfileDataService {

    private final Logger log = LoggerFactory.getLogger(UserProfileDataServiceImpl.class);

    private final UserProfileDataRepository userProfileDataRepository;

    private final UserProfileDataMapper userProfileDataMapper;

    public UserProfileDataServiceImpl(UserProfileDataRepository userProfileDataRepository, UserProfileDataMapper userProfileDataMapper) {
        this.userProfileDataRepository = userProfileDataRepository;
        this.userProfileDataMapper = userProfileDataMapper;
    }

    /**
     * Save a userProfileData.
     *
     * @param userProfileDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserProfileDataDTO save(UserProfileDataDTO userProfileDataDTO) {
        log.debug("Request to save UserProfileData : {}", userProfileDataDTO);
        UserProfileData userProfileData = userProfileDataMapper.toEntity(userProfileDataDTO);
        userProfileData = userProfileDataRepository.save(userProfileData);
        return userProfileDataMapper.toDto(userProfileData);
    }

    /**
     * Get all the userProfileData.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserProfileDataDTO> findAll() {
        log.debug("Request to get all UserProfileData");
        return userProfileDataRepository.findAll().stream()
            .map(userProfileDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userProfileData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfileDataDTO> findOne(Long id) {
        log.debug("Request to get UserProfileData : {}", id);
        return userProfileDataRepository.findById(id)
            .map(userProfileDataMapper::toDto);
    }

    /**
     * Delete the userProfileData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserProfileData : {}", id);
        userProfileDataRepository.deleteById(id);
    }
}
