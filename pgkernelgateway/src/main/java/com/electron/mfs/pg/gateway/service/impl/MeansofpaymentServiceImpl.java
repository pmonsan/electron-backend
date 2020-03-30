package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.MeansofpaymentService;
import com.electron.mfs.pg.gateway.domain.Meansofpayment;
import com.electron.mfs.pg.gateway.repository.MeansofpaymentRepository;
import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentDTO;
import com.electron.mfs.pg.gateway.service.mapper.MeansofpaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Meansofpayment}.
 */
@Service
@Transactional
public class MeansofpaymentServiceImpl implements MeansofpaymentService {

    private final Logger log = LoggerFactory.getLogger(MeansofpaymentServiceImpl.class);

    private final MeansofpaymentRepository meansofpaymentRepository;

    private final MeansofpaymentMapper meansofpaymentMapper;

    public MeansofpaymentServiceImpl(MeansofpaymentRepository meansofpaymentRepository, MeansofpaymentMapper meansofpaymentMapper) {
        this.meansofpaymentRepository = meansofpaymentRepository;
        this.meansofpaymentMapper = meansofpaymentMapper;
    }

    /**
     * Save a meansofpayment.
     *
     * @param meansofpaymentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MeansofpaymentDTO save(MeansofpaymentDTO meansofpaymentDTO) {
        log.debug("Request to save Meansofpayment : {}", meansofpaymentDTO);
        Meansofpayment meansofpayment = meansofpaymentMapper.toEntity(meansofpaymentDTO);
        meansofpayment = meansofpaymentRepository.save(meansofpayment);
        return meansofpaymentMapper.toDto(meansofpayment);
    }

    /**
     * Get all the meansofpayments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeansofpaymentDTO> findAll() {
        log.debug("Request to get all Meansofpayments");
        return meansofpaymentRepository.findAll().stream()
            .map(meansofpaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one meansofpayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MeansofpaymentDTO> findOne(Long id) {
        log.debug("Request to get Meansofpayment : {}", id);
        return meansofpaymentRepository.findById(id)
            .map(meansofpaymentMapper::toDto);
    }

    /**
     * Delete the meansofpayment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Meansofpayment : {}", id);
        meansofpaymentRepository.deleteById(id);
    }
}
