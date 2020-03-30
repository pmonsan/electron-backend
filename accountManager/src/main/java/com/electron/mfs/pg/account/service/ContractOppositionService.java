package com.electron.mfs.pg.account.service;

import com.electron.mfs.pg.account.service.dto.ContractOppositionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.account.domain.ContractOpposition}.
 */
public interface ContractOppositionService {

    /**
     * Save a contractOpposition.
     *
     * @param contractOppositionDTO the entity to save.
     * @return the persisted entity.
     */
    ContractOppositionDTO save(ContractOppositionDTO contractOppositionDTO);

    /**
     * Get all the contractOppositions.
     *
     * @return the list of entities.
     */
    List<ContractOppositionDTO> findAll();


    /**
     * Get the "id" contractOpposition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContractOppositionDTO> findOne(Long id);

    /**
     * Delete the "id" contractOpposition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
