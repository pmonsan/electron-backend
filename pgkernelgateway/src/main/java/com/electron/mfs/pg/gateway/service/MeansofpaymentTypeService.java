package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.MeansofpaymentType}.
 */
public interface MeansofpaymentTypeService {

    /**
     * Save a meansofpaymentType.
     *
     * @param meansofpaymentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    MeansofpaymentTypeDTO save(MeansofpaymentTypeDTO meansofpaymentTypeDTO);

    /**
     * Get all the meansofpaymentTypes.
     *
     * @return the list of entities.
     */
    List<MeansofpaymentTypeDTO> findAll();


    /**
     * Get the "id" meansofpaymentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeansofpaymentTypeDTO> findOne(Long id);

    /**
     * Delete the "id" meansofpaymentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
