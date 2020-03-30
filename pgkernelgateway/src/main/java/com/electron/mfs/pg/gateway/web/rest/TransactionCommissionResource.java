package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.TransactionCommissionService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.TransactionCommissionDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.TransactionCommission}.
 */
@RestController
@RequestMapping("/api")
public class TransactionCommissionResource {

    private final Logger log = LoggerFactory.getLogger(TransactionCommissionResource.class);

    private static final String ENTITY_NAME = "transactionCommission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionCommissionService transactionCommissionService;

    public TransactionCommissionResource(TransactionCommissionService transactionCommissionService) {
        this.transactionCommissionService = transactionCommissionService;
    }

    /**
     * {@code POST  /transaction-commissions} : Create a new transactionCommission.
     *
     * @param transactionCommissionDTO the transactionCommissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionCommissionDTO, or with status {@code 400 (Bad Request)} if the transactionCommission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-commissions")
    public ResponseEntity<TransactionCommissionDTO> createTransactionCommission(@Valid @RequestBody TransactionCommissionDTO transactionCommissionDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionCommission : {}", transactionCommissionDTO);
        if (transactionCommissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionCommission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionCommissionDTO result = transactionCommissionService.save(transactionCommissionDTO);
        return ResponseEntity.created(new URI("/api/transaction-commissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-commissions} : Updates an existing transactionCommission.
     *
     * @param transactionCommissionDTO the transactionCommissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionCommissionDTO,
     * or with status {@code 400 (Bad Request)} if the transactionCommissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionCommissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-commissions")
    public ResponseEntity<TransactionCommissionDTO> updateTransactionCommission(@Valid @RequestBody TransactionCommissionDTO transactionCommissionDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionCommission : {}", transactionCommissionDTO);
        if (transactionCommissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionCommissionDTO result = transactionCommissionService.save(transactionCommissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionCommissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-commissions} : get all the transactionCommissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionCommissions in body.
     */
    @GetMapping("/transaction-commissions")
    public List<TransactionCommissionDTO> getAllTransactionCommissions() {
        log.debug("REST request to get all TransactionCommissions");
        return transactionCommissionService.findAll();
    }

    /**
     * {@code GET  /transaction-commissions/:id} : get the "id" transactionCommission.
     *
     * @param id the id of the transactionCommissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionCommissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-commissions/{id}")
    public ResponseEntity<TransactionCommissionDTO> getTransactionCommission(@PathVariable Long id) {
        log.debug("REST request to get TransactionCommission : {}", id);
        Optional<TransactionCommissionDTO> transactionCommissionDTO = transactionCommissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionCommissionDTO);
    }

    /**
     * {@code DELETE  /transaction-commissions/:id} : delete the "id" transactionCommission.
     *
     * @param id the id of the transactionCommissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-commissions/{id}")
    public ResponseEntity<Void> deleteTransactionCommission(@PathVariable Long id) {
        log.debug("REST request to delete TransactionCommission : {}", id);
        transactionCommissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
