package com.electron.mfs.pg.mdm.service;

import com.electron.mfs.pg.mdm.service.dto.TransactionChannelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.electron.mfs.pg.mdm.domain.TransactionChannel}.
 */
public interface TransactionChannelService {

    /**
     * Save a transactionChannel.
     *
     * @param transactionChannelDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionChannelDTO save(TransactionChannelDTO transactionChannelDTO);

    /**
     * Get all the transactionChannels.
     *
     * @return the list of entities.
     */
    List<TransactionChannelDTO> findAll();


    /**
     * Get the "id" transactionChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionChannelDTO> findOne(Long id);

    /**
     * Delete the "id" transactionChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
