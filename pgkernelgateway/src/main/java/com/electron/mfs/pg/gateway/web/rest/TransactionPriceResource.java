package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.TransactionPriceService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.TransactionPriceDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.TransactionPrice}.
 */
@RestController
@RequestMapping("/api")
public class TransactionPriceResource {

    private final Logger log = LoggerFactory.getLogger(TransactionPriceResource.class);

    private static final String ENTITY_NAME = "transactionPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionPriceService transactionPriceService;

    public TransactionPriceResource(TransactionPriceService transactionPriceService) {
        this.transactionPriceService = transactionPriceService;
    }

    /**
     * {@code POST  /transaction-prices} : Create a new transactionPrice.
     *
     * @param transactionPriceDTO the transactionPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionPriceDTO, or with status {@code 400 (Bad Request)} if the transactionPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-prices")
    public ResponseEntity<TransactionPriceDTO> createTransactionPrice(@Valid @RequestBody TransactionPriceDTO transactionPriceDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionPrice : {}", transactionPriceDTO);
        if (transactionPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionPriceDTO result = transactionPriceService.save(transactionPriceDTO);
        return ResponseEntity.created(new URI("/api/transaction-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-prices} : Updates an existing transactionPrice.
     *
     * @param transactionPriceDTO the transactionPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionPriceDTO,
     * or with status {@code 400 (Bad Request)} if the transactionPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-prices")
    public ResponseEntity<TransactionPriceDTO> updateTransactionPrice(@Valid @RequestBody TransactionPriceDTO transactionPriceDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionPrice : {}", transactionPriceDTO);
        if (transactionPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionPriceDTO result = transactionPriceService.save(transactionPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-prices} : get all the transactionPrices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionPrices in body.
     */
    @GetMapping("/transaction-prices")
    public List<TransactionPriceDTO> getAllTransactionPrices() {
        log.debug("REST request to get all TransactionPrices");
        return transactionPriceService.findAll();
    }

    /**
     * {@code GET  /transaction-prices/:id} : get the "id" transactionPrice.
     *
     * @param id the id of the transactionPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-prices/{id}")
    public ResponseEntity<TransactionPriceDTO> getTransactionPrice(@PathVariable Long id) {
        log.debug("REST request to get TransactionPrice : {}", id);
        Optional<TransactionPriceDTO> transactionPriceDTO = transactionPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionPriceDTO);
    }

    /**
     * {@code DELETE  /transaction-prices/:id} : delete the "id" transactionPrice.
     *
     * @param id the id of the transactionPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-prices/{id}")
    public ResponseEntity<Void> deleteTransactionPrice(@PathVariable Long id) {
        log.debug("REST request to delete TransactionPrice : {}", id);
        transactionPriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
