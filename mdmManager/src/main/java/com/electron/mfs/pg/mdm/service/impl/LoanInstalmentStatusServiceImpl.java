package com.electron.mfs.pg.mdm.service.impl;

import com.electron.mfs.pg.mdm.service.LoanInstalmentStatusService;
import com.electron.mfs.pg.mdm.domain.LoanInstalmentStatus;
import com.electron.mfs.pg.mdm.repository.LoanInstalmentStatusRepository;
import com.electron.mfs.pg.mdm.service.dto.LoanInstalmentStatusDTO;
import com.electron.mfs.pg.mdm.service.mapper.LoanInstalmentStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LoanInstalmentStatus}.
 */
@Service
@Transactional
public class LoanInstalmentStatusServiceImpl implements LoanInstalmentStatusService {

    private final Logger log = LoggerFactory.getLogger(LoanInstalmentStatusServiceImpl.class);

    private final LoanInstalmentStatusRepository loanInstalmentStatusRepository;

    private final LoanInstalmentStatusMapper loanInstalmentStatusMapper;

    public LoanInstalmentStatusServiceImpl(LoanInstalmentStatusRepository loanInstalmentStatusRepository, LoanInstalmentStatusMapper loanInstalmentStatusMapper) {
        this.loanInstalmentStatusRepository = loanInstalmentStatusRepository;
        this.loanInstalmentStatusMapper = loanInstalmentStatusMapper;
    }

    /**
     * Save a loanInstalmentStatus.
     *
     * @param loanInstalmentStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LoanInstalmentStatusDTO save(LoanInstalmentStatusDTO loanInstalmentStatusDTO) {
        log.debug("Request to save LoanInstalmentStatus : {}", loanInstalmentStatusDTO);
        LoanInstalmentStatus loanInstalmentStatus = loanInstalmentStatusMapper.toEntity(loanInstalmentStatusDTO);
        loanInstalmentStatus = loanInstalmentStatusRepository.save(loanInstalmentStatus);
        return loanInstalmentStatusMapper.toDto(loanInstalmentStatus);
    }

    /**
     * Get all the loanInstalmentStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoanInstalmentStatusDTO> findAll() {
        log.debug("Request to get all LoanInstalmentStatuses");
        return loanInstalmentStatusRepository.findAll().stream()
            .map(loanInstalmentStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one loanInstalmentStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LoanInstalmentStatusDTO> findOne(Long id) {
        log.debug("Request to get LoanInstalmentStatus : {}", id);
        return loanInstalmentStatusRepository.findById(id)
            .map(loanInstalmentStatusMapper::toDto);
    }

    /**
     * Delete the loanInstalmentStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoanInstalmentStatus : {}", id);
        loanInstalmentStatusRepository.deleteById(id);
    }
}
