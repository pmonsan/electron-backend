package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.TransactionStatusService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.TransactionStatusDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.TransactionStatus}.
 */
@RestController
@RequestMapping("/api")
public class TransactionStatusResource {

    private final Logger log = LoggerFactory.getLogger(TransactionStatusResource.class);

    private static final String ENTITY_NAME = "transactionStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionStatusService transactionStatusService;

    public TransactionStatusResource(TransactionStatusService transactionStatusService) {
        this.transactionStatusService = transactionStatusService;
    }

    /**
     * {@code POST  /transaction-statuses} : Create a new transactionStatus.
     *
     * @param transactionStatusDTO the transactionStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionStatusDTO, or with status {@code 400 (Bad Request)} if the transactionStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-statuses")
    public ResponseEntity<TransactionStatusDTO> createTransactionStatus(@Valid @RequestBody TransactionStatusDTO transactionStatusDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionStatus : {}", transactionStatusDTO);
        if (transactionStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionStatusDTO result = transactionStatusService.save(transactionStatusDTO);
        return ResponseEntity.created(new URI("/api/transaction-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-statuses} : Updates an existing transactionStatus.
     *
     * @param transactionStatusDTO the transactionStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionStatusDTO,
     * or with status {@code 400 (Bad Request)} if the transactionStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-statuses")
    public ResponseEntity<TransactionStatusDTO> updateTransactionStatus(@Valid @RequestBody TransactionStatusDTO transactionStatusDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionStatus : {}", transactionStatusDTO);
        if (transactionStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionStatusDTO result = transactionStatusService.save(transactionStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-statuses} : get all the transactionStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionStatuses in body.
     */
    @GetMapping("/transaction-statuses")
    public List<TransactionStatusDTO> getAllTransactionStatuses() {
        log.debug("REST request to get all TransactionStatuses");
        return transactionStatusService.findAll();
    }

    /**
     * {@code GET  /transaction-statuses/:id} : get the "id" transactionStatus.
     *
     * @param id the id of the transactionStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-statuses/{id}")
    public ResponseEntity<TransactionStatusDTO> getTransactionStatus(@PathVariable Long id) {
        log.debug("REST request to get TransactionStatus : {}", id);
        Optional<TransactionStatusDTO> transactionStatusDTO = transactionStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionStatusDTO);
    }

    /**
     * {@code DELETE  /transaction-statuses/:id} : delete the "id" transactionStatus.
     *
     * @param id the id of the transactionStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-statuses/{id}")
    public ResponseEntity<Void> deleteTransactionStatus(@PathVariable Long id) {
        log.debug("REST request to delete TransactionStatus : {}", id);
        transactionStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
