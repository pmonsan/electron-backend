package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.TransactionPropertyService;
import com.electron.mfs.pg.mdm.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.mdm.service.dto.TransactionPropertyDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.mdm.domain.TransactionProperty}.
 */
@RestController
@RequestMapping("/api")
public class TransactionPropertyResource {

    private final Logger log = LoggerFactory.getLogger(TransactionPropertyResource.class);

    private static final String ENTITY_NAME = "mdmManagerTransactionProperty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionPropertyService transactionPropertyService;

    public TransactionPropertyResource(TransactionPropertyService transactionPropertyService) {
        this.transactionPropertyService = transactionPropertyService;
    }

    /**
     * {@code POST  /transaction-properties} : Create a new transactionProperty.
     *
     * @param transactionPropertyDTO the transactionPropertyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionPropertyDTO, or with status {@code 400 (Bad Request)} if the transactionProperty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-properties")
    public ResponseEntity<TransactionPropertyDTO> createTransactionProperty(@Valid @RequestBody TransactionPropertyDTO transactionPropertyDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionProperty : {}", transactionPropertyDTO);
        if (transactionPropertyDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionProperty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionPropertyDTO result = transactionPropertyService.save(transactionPropertyDTO);
        return ResponseEntity.created(new URI("/api/transaction-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-properties} : Updates an existing transactionProperty.
     *
     * @param transactionPropertyDTO the transactionPropertyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionPropertyDTO,
     * or with status {@code 400 (Bad Request)} if the transactionPropertyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionPropertyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-properties")
    public ResponseEntity<TransactionPropertyDTO> updateTransactionProperty(@Valid @RequestBody TransactionPropertyDTO transactionPropertyDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionProperty : {}", transactionPropertyDTO);
        if (transactionPropertyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionPropertyDTO result = transactionPropertyService.save(transactionPropertyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionPropertyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-properties} : get all the transactionProperties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionProperties in body.
     */
    @GetMapping("/transaction-properties")
    public List<TransactionPropertyDTO> getAllTransactionProperties() {
        log.debug("REST request to get all TransactionProperties");
        return transactionPropertyService.findAll();
    }

    /**
     * {@code GET  /transaction-properties/:id} : get the "id" transactionProperty.
     *
     * @param id the id of the transactionPropertyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionPropertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-properties/{id}")
    public ResponseEntity<TransactionPropertyDTO> getTransactionProperty(@PathVariable Long id) {
        log.debug("REST request to get TransactionProperty : {}", id);
        Optional<TransactionPropertyDTO> transactionPropertyDTO = transactionPropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionPropertyDTO);
    }

    /**
     * {@code DELETE  /transaction-properties/:id} : delete the "id" transactionProperty.
     *
     * @param id the id of the transactionPropertyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-properties/{id}")
    public ResponseEntity<Void> deleteTransactionProperty(@PathVariable Long id) {
        log.debug("REST request to delete TransactionProperty : {}", id);
        transactionPropertyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
