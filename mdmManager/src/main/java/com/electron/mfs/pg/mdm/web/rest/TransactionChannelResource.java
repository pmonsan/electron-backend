package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.TransactionChannelService;
import com.electron.mfs.pg.mdm.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.mdm.service.dto.TransactionChannelDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.electron.mfs.pg.mdm.domain.TransactionChannel}.
 */
@RestController
@RequestMapping("/api")
public class TransactionChannelResource {

    private final Logger log = LoggerFactory.getLogger(TransactionChannelResource.class);

    private static final String ENTITY_NAME = "mdmManagerTransactionChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionChannelService transactionChannelService;

    public TransactionChannelResource(TransactionChannelService transactionChannelService) {
        this.transactionChannelService = transactionChannelService;
    }

    /**
     * {@code POST  /transaction-channels} : Create a new transactionChannel.
     *
     * @param transactionChannelDTO the transactionChannelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionChannelDTO, or with status {@code 400 (Bad Request)} if the transactionChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-channels")
    public ResponseEntity<TransactionChannelDTO> createTransactionChannel(@Valid @RequestBody TransactionChannelDTO transactionChannelDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionChannel : {}", transactionChannelDTO);
        if (transactionChannelDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionChannelDTO result = transactionChannelService.save(transactionChannelDTO);
        return ResponseEntity.created(new URI("/api/transaction-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-channels} : Updates an existing transactionChannel.
     *
     * @param transactionChannelDTO the transactionChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionChannelDTO,
     * or with status {@code 400 (Bad Request)} if the transactionChannelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-channels")
    public ResponseEntity<TransactionChannelDTO> updateTransactionChannel(@Valid @RequestBody TransactionChannelDTO transactionChannelDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionChannel : {}", transactionChannelDTO);
        if (transactionChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionChannelDTO result = transactionChannelService.save(transactionChannelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-channels} : get all the transactionChannels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionChannels in body.
     */
    @GetMapping("/transaction-channels")
    public List<TransactionChannelDTO> getAllTransactionChannels() {
        log.debug("REST request to get all TransactionChannels");
        return transactionChannelService.findAll();
    }

    /**
     * {@code GET  /transaction-channels/:id} : get the "id" transactionChannel.
     *
     * @param id the id of the transactionChannelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionChannelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-channels/{id}")
    public ResponseEntity<TransactionChannelDTO> getTransactionChannel(@PathVariable Long id) {
        log.debug("REST request to get TransactionChannel : {}", id);
        Optional<TransactionChannelDTO> transactionChannelDTO = transactionChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionChannelDTO);
    }

    /**
     * {@code DELETE  /transaction-channels/:id} : delete the "id" transactionChannel.
     *
     * @param id the id of the transactionChannelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-channels/{id}")
    public ResponseEntity<Void> deleteTransactionChannel(@PathVariable Long id) {
        log.debug("REST request to delete TransactionChannel : {}", id);
        transactionChannelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
