package com.electron.mfs.pg.gateway.service.impl;

import com.electron.mfs.pg.gateway.service.PgMessageModelService;
import com.electron.mfs.pg.gateway.domain.PgMessageModel;
import com.electron.mfs.pg.gateway.repository.PgMessageModelRepository;
import com.electron.mfs.pg.gateway.service.dto.PgMessageModelDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgMessageModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PgMessageModel}.
 */
@Service
@Transactional
public class PgMessageModelServiceImpl implements PgMessageModelService {

    private final Logger log = LoggerFactory.getLogger(PgMessageModelServiceImpl.class);

    private final PgMessageModelRepository pgMessageModelRepository;

    private final PgMessageModelMapper pgMessageModelMapper;

    public PgMessageModelServiceImpl(PgMessageModelRepository pgMessageModelRepository, PgMessageModelMapper pgMessageModelMapper) {
        this.pgMessageModelRepository = pgMessageModelRepository;
        this.pgMessageModelMapper = pgMessageModelMapper;
    }

    /**
     * Save a pgMessageModel.
     *
     * @param pgMessageModelDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PgMessageModelDTO save(PgMessageModelDTO pgMessageModelDTO) {
        log.debug("Request to save PgMessageModel : {}", pgMessageModelDTO);
        PgMessageModel pgMessageModel = pgMessageModelMapper.toEntity(pgMessageModelDTO);
        pgMessageModel = pgMessageModelRepository.save(pgMessageModel);
        return pgMessageModelMapper.toDto(pgMessageModel);
    }

    /**
     * Get all the pgMessageModels.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PgMessageModelDTO> findAll() {
        log.debug("Request to get all PgMessageModels");
        return pgMessageModelRepository.findAll().stream()
            .map(pgMessageModelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pgMessageModel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PgMessageModelDTO> findOne(Long id) {
        log.debug("Request to get PgMessageModel : {}", id);
        return pgMessageModelRepository.findById(id)
            .map(pgMessageModelMapper::toDto);
    }

    /**
     * Delete the pgMessageModel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PgMessageModel : {}", id);
        pgMessageModelRepository.deleteById(id);
    }
}
