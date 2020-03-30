package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.DetailContractService;
import com.electron.mfs.pg.gateway.domain.DetailContract;
import com.electron.mfs.pg.gateway.repository.DetailContractRepository;
import com.electron.mfs.pg.gateway.service.dto.DetailContractDTO;
import com.electron.mfs.pg.gateway.service.mapper.DetailContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DetailContract}.
 */
@Service
@Transactional
public class DetailContractServiceImpl implements DetailContractService {

    private final Logger log = LoggerFactory.getLogger(DetailContractServiceImpl.class);

    private final DetailContractRepository detailContractRepository;

    private final DetailContractMapper detailContractMapper;

    public DetailContractServiceImpl(DetailContractRepository detailContractRepository, DetailContractMapper detailContractMapper) {
        this.detailContractRepository = detailContractRepository;
        this.detailContractMapper = detailContractMapper;
    }

    /**
     * Save a detailContract.
     *
     * @param detailContractDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DetailContractDTO save(DetailContractDTO detailContractDTO) {
        log.debug("Request to save DetailContract : {}", detailContractDTO);
        DetailContract detailContract = detailContractMapper.toEntity(detailContractDTO);
        detailContract = detailContractRepository.save(detailContract);
        return detailContractMapper.toDto(detailContract);
    }

    /**
     * Get all the detailContracts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DetailContractDTO> findAll() {
        log.debug("Request to get all DetailContracts");
        return detailContractRepository.findAll().stream()
            .map(detailContractMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one detailContract by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetailContractDTO> findOne(Long id) {
        log.debug("Request to get DetailContract : {}", id);
        return detailContractRepository.findById(id)
            .map(detailContractMapper::toDto);
    }

    /**
     * Delete the detailContract by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailContract : {}", id);
        detailContractRepository.deleteById(id);
    }
}
