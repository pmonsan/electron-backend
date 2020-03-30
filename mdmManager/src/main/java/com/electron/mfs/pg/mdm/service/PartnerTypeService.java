package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.PartnerTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.PartnerType}.
 */
public interface PartnerTypeService {

    /**
     * Save a partnerType.
     *
     * @param partnerTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PartnerTypeDTO save(PartnerTypeDTO partnerTypeDTO);

    /**
     * Get all the partnerTypes.
     *
     * @return the list of entities.
     */
    List<PartnerTypeDTO> findAll();


    /**
     * Get the "id" partnerType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartnerTypeDTO> findOne(Long id);

    /**
     * Delete the "id" partnerType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
