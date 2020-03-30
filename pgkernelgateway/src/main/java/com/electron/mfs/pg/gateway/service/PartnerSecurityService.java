package com.electron.mfs.pg.gateway.service;

import com.electron.mfs.pg.gateway.service.dto.PartnerSecurityDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.gateway.domain.PartnerSecurity}.
 */
public interface PartnerSecurityService {

    /**
     * Save a partnerSecurity.
     *
     * @param partnerSecurityDTO the entity to save.
     * @return the persisted entity.
     */
    PartnerSecurityDTO save(PartnerSecurityDTO partnerSecurityDTO);

    /**
     * Get all the partnerSecurities.
     *
     * @return the list of entities.
     */
    List<PartnerSecurityDTO> findAll();


    /**
     * Get the "id" partnerSecurity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartnerSecurityDTO> findOne(Long id);

    /**
     * Delete the "id" partnerSecurity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
