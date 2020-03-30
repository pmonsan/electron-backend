package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.Meansofpayment}.
 */
public interface MeansofpaymentService {

    /**
     * Save a meansofpayment.
     *
     * @param meansofpaymentDTO the entity to save.
     * @return the persisted entity.
     */
    MeansofpaymentDTO save(MeansofpaymentDTO meansofpaymentDTO);

    /**
     * Get all the meansofpayments.
     *
     * @return the list of entities.
     */
    List<MeansofpaymentDTO> findAll();


    /**
     * Get the "id" meansofpayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeansofpaymentDTO> findOne(Long id);

    /**
     * Delete the "id" meansofpayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
