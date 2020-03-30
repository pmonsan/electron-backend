package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.DetailContractDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.DetailContract}.
 */
public interface DetailContractService {

    /**
     * Save a detailContract.
     *
     * @param detailContractDTO the entity to save.
     * @return the persisted entity.
     */
    DetailContractDTO save(DetailContractDTO detailContractDTO);

    /**
     * Get all the detailContracts.
     *
     * @return the list of entities.
     */
    List<DetailContractDTO> findAll();


    /**
     * Get the "id" detailContract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailContractDTO> findOne(Long id);

    /**
     * Delete the "id" detailContract.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
