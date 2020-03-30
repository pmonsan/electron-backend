package com.electron.mfs.pg.subscription.service.impl;

import com.electron.mfs.pg.subscription.service.PartnerSecurityService;
import com.electron.mfs.pg.subscription.domain.PartnerSecurity;
import com.electron.mfs.pg.subscription.repository.PartnerSecurityRepository;
import com.electron.mfs.pg.subscription.service.dto.PartnerSecurityDTO;
import com.electron.mfs.pg.subscription.service.mapper.PartnerSecurityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartnerSecurity}.
 */
@Service
@Transactional
public class PartnerSecurityServiceImpl implements PartnerSecurityService {

    private final Logger log = LoggerFactory.getLogger(PartnerSecurityServiceImpl.class);

    private final PartnerSecurityRepository partnerSecurityRepository;

    private final PartnerSecurityMapper partnerSecurityMapper;

    public PartnerSecurityServiceImpl(PartnerSecurityRepository partnerSecurityRepository, PartnerSecurityMapper partnerSecurityMapper) {
        this.partnerSecurityRepository = partnerSecurityRepository;
        this.partnerSecurityMapper = partnerSecurityMapper;
    }

    /**
     * Save a partnerSecurity.
     *
     * @param partnerSecurityDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartnerSecurityDTO save(PartnerSecurityDTO partnerSecurityDTO) {
        log.debug("Request to save PartnerSecurity : {}", partnerSecurityDTO);
        PartnerSecurity partnerSecurity = partnerSecurityMapper.toEntity(partnerSecurityDTO);
        partnerSecurity = partnerSecurityRepository.save(partnerSecurity);
        return partnerSecurityMapper.toDto(partnerSecurity);
    }

    /**
     * Get all the partnerSecurities.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartnerSecurityDTO> findAll() {
        log.debug("Request to get all PartnerSecurities");
        return partnerSecurityRepository.findAll().stream()
            .map(partnerSecurityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partnerSecurity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartnerSecurityDTO> findOne(Long id) {
        log.debug("Request to get PartnerSecurity : {}", id);
        return partnerSecurityRepository.findById(id)
            .map(partnerSecurityMapper::toDto);
    }

    /**
     * Delete the partnerSecurity by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartnerSecurity : {}", id);
        partnerSecurityRepository.deleteById(id);
    }
}
