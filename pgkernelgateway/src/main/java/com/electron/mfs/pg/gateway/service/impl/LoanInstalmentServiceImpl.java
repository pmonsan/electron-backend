package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.LoanInstalmentService;
import com.electron.mfs.pg.gateway.domain.LoanInstalment;
import com.electron.mfs.pg.gateway.repository.LoanInstalmentRepository;
import com.electron.mfs.pg.gateway.service.dto.LoanInstalmentDTO;
import com.electron.mfs.pg.gateway.service.mapper.LoanInstalmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LoanInstalment}.
 */
@Service
@Transactional
public class LoanInstalmentServiceImpl implements LoanInstalmentService {

    private final Logger log = LoggerFactory.getLogger(LoanInstalmentServiceImpl.class);

    private final LoanInstalmentRepository loanInstalmentRepository;

    private final LoanInstalmentMapper loanInstalmentMapper;

    public LoanInstalmentServiceImpl(LoanInstalmentRepository loanInstalmentRepository, LoanInstalmentMapper loanInstalmentMapper) {
        this.loanInstalmentRepository = loanInstalmentRepository;
        this.loanInstalmentMapper = loanInstalmentMapper;
    }

    /**
     * Save a loanInstalment.
     *
     * @param loanInstalmentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LoanInstalmentDTO save(LoanInstalmentDTO loanInstalmentDTO) {
        log.debug("Request to save LoanInstalment : {}", loanInstalmentDTO);
        LoanInstalment loanInstalment = loanInstalmentMapper.toEntity(loanInstalmentDTO);
        loanInstalment = loanInstalmentRepository.save(loanInstalment);
        return loanInstalmentMapper.toDto(loanInstalment);
    }

    /**
     * Get all the loanInstalments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoanInstalmentDTO> findAll() {
        log.debug("Request to get all LoanInstalments");
        return loanInstalmentRepository.findAll().stream()
            .map(loanInstalmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one loanInstalment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LoanInstalmentDTO> findOne(Long id) {
        log.debug("Request to get LoanInstalment : {}", id);
        return loanInstalmentRepository.findById(id)
            .map(loanInstalmentMapper::toDto);
    }

    /**
     * Delete the loanInstalment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoanInstalment : {}", id);
        loanInstalmentRepository.deleteById(id);
    }
}
