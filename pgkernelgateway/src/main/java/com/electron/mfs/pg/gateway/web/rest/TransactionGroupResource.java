package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.TransactionGroupService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.TransactionGroupDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.TransactionGroup}.
 */
@RestController
@RequestMapping("/api")
public class TransactionGroupResource {

    private final Logger log = LoggerFactory.getLogger(TransactionGroupResource.class);

    private static final String ENTITY_NAME = "transactionGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionGroupService transactionGroupService;

    public TransactionGroupResource(TransactionGroupService transactionGroupService) {
        this.transactionGroupService = transactionGroupService;
    }

    /**
     * {@code POST  /transaction-groups} : Create a new transactionGroup.
     *
     * @param transactionGroupDTO the transactionGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionGroupDTO, or with status {@code 400 (Bad Request)} if the transactionGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-groups")
    public ResponseEntity<TransactionGroupDTO> createTransactionGroup(@Valid @RequestBody TransactionGroupDTO transactionGroupDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionGroup : {}", transactionGroupDTO);
        if (transactionGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionGroupDTO result = transactionGroupService.save(transactionGroupDTO);
        return ResponseEntity.created(new URI("/api/transaction-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-groups} : Updates an existing transactionGroup.
     *
     * @param transactionGroupDTO the transactionGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionGroupDTO,
     * or with status {@code 400 (Bad Request)} if the transactionGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-groups")
    public ResponseEntity<TransactionGroupDTO> updateTransactionGroup(@Valid @RequestBody TransactionGroupDTO transactionGroupDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionGroup : {}", transactionGroupDTO);
        if (transactionGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionGroupDTO result = transactionGroupService.save(transactionGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-groups} : get all the transactionGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionGroups in body.
     */
    @GetMapping("/transaction-groups")
    public List<TransactionGroupDTO> getAllTransactionGroups() {
        log.debug("REST request to get all TransactionGroups");
        return transactionGroupService.findAll();
    }

    /**
     * {@code GET  /transaction-groups/:id} : get the "id" transactionGroup.
     *
     * @param id the id of the transactionGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-groups/{id}")
    public ResponseEntity<TransactionGroupDTO> getTransactionGroup(@PathVariable Long id) {
        log.debug("REST request to get TransactionGroup : {}", id);
        Optional<TransactionGroupDTO> transactionGroupDTO = transactionGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionGroupDTO);
    }

    /**
     * {@code DELETE  /transaction-groups/:id} : delete the "id" transactionGroup.
     *
     * @param id the id of the transactionGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-groups/{id}")
    public ResponseEntity<Void> deleteTransactionGroup(@PathVariable Long id) {
        log.debug("REST request to delete TransactionGroup : {}", id);
        transactionGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
