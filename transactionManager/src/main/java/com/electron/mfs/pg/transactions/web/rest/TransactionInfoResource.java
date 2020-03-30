package com.electron.mfs.pg.transactions.web.rest;

import com.electron.mfs.pg.transactions.service.TransactionInfoService;
import com.electron.mfs.pg.transactions.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.transactions.service.dto.TransactionInfoDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.transactions.domain.TransactionInfo}.
 */
@RestController
@RequestMapping("/api")
public class TransactionInfoResource {

    private final Logger log = LoggerFactory.getLogger(TransactionInfoResource.class);

    private static final String ENTITY_NAME = "transactionManagerTransactionInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionInfoService transactionInfoService;

    public TransactionInfoResource(TransactionInfoService transactionInfoService) {
        this.transactionInfoService = transactionInfoService;
    }

    /**
     * {@code POST  /transaction-infos} : Create a new transactionInfo.
     *
     * @param transactionInfoDTO the transactionInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionInfoDTO, or with status {@code 400 (Bad Request)} if the transactionInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-infos")
    public ResponseEntity<TransactionInfoDTO> createTransactionInfo(@Valid @RequestBody TransactionInfoDTO transactionInfoDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionInfo : {}", transactionInfoDTO);
        if (transactionInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionInfoDTO result = transactionInfoService.save(transactionInfoDTO);
        return ResponseEntity.created(new URI("/api/transaction-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-infos} : Updates an existing transactionInfo.
     *
     * @param transactionInfoDTO the transactionInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionInfoDTO,
     * or with status {@code 400 (Bad Request)} if the transactionInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-infos")
    public ResponseEntity<TransactionInfoDTO> updateTransactionInfo(@Valid @RequestBody TransactionInfoDTO transactionInfoDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionInfo : {}", transactionInfoDTO);
        if (transactionInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionInfoDTO result = transactionInfoService.save(transactionInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-infos} : get all the transactionInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionInfos in body.
     */
    @GetMapping("/transaction-infos")
    public List<TransactionInfoDTO> getAllTransactionInfos() {
        log.debug("REST request to get all TransactionInfos");
        return transactionInfoService.findAll();
    }

    /**
     * {@code GET  /transaction-infos/:id} : get the "id" transactionInfo.
     *
     * @param id the id of the transactionInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-infos/{id}")
    public ResponseEntity<TransactionInfoDTO> getTransactionInfo(@PathVariable Long id) {
        log.debug("REST request to get TransactionInfo : {}", id);
        Optional<TransactionInfoDTO> transactionInfoDTO = transactionInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionInfoDTO);
    }

    /**
     * {@code DELETE  /transaction-infos/:id} : delete the "id" transactionInfo.
     *
     * @param id the id of the transactionInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-infos/{id}")
    public ResponseEntity<Void> deleteTransactionInfo(@PathVariable Long id) {
        log.debug("REST request to delete TransactionInfo : {}", id);
        transactionInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
