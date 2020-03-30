package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.service.PgAccountService;
import com.electron.mfs.pg.account.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.account.service.dto.PgAccountDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.account.domain.PgAccount}.
 */
@RestController
@RequestMapping("/api")
public class PgAccountResource {

    private final Logger log = LoggerFactory.getLogger(PgAccountResource.class);

    private static final String ENTITY_NAME = "accountManagerPgAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgAccountService pgAccountService;

    public PgAccountResource(PgAccountService pgAccountService) {
        this.pgAccountService = pgAccountService;
    }

    /**
     * {@code POST  /pg-accounts} : Create a new pgAccount.
     *
     * @param pgAccountDTO the pgAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgAccountDTO, or with status {@code 400 (Bad Request)} if the pgAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-accounts")
    public ResponseEntity<PgAccountDTO> createPgAccount(@Valid @RequestBody PgAccountDTO pgAccountDTO) throws URISyntaxException {
        log.debug("REST request to save PgAccount : {}", pgAccountDTO);
        if (pgAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgAccountDTO result = pgAccountService.save(pgAccountDTO);
        return ResponseEntity.created(new URI("/api/pg-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-accounts} : Updates an existing pgAccount.
     *
     * @param pgAccountDTO the pgAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgAccountDTO,
     * or with status {@code 400 (Bad Request)} if the pgAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-accounts")
    public ResponseEntity<PgAccountDTO> updatePgAccount(@Valid @RequestBody PgAccountDTO pgAccountDTO) throws URISyntaxException {
        log.debug("REST request to update PgAccount : {}", pgAccountDTO);
        if (pgAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgAccountDTO result = pgAccountService.save(pgAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-accounts} : get all the pgAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgAccounts in body.
     */
    @GetMapping("/pg-accounts")
    public List<PgAccountDTO> getAllPgAccounts() {
        log.debug("REST request to get all PgAccounts");
        return pgAccountService.findAll();
    }

    /**
     * {@code GET  /pg-accounts/:id} : get the "id" pgAccount.
     *
     * @param id the id of the pgAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-accounts/{id}")
    public ResponseEntity<PgAccountDTO> getPgAccount(@PathVariable Long id) {
        log.debug("REST request to get PgAccount : {}", id);
        Optional<PgAccountDTO> pgAccountDTO = pgAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgAccountDTO);
    }

    /**
     * {@code DELETE  /pg-accounts/:id} : delete the "id" pgAccount.
     *
     * @param id the id of the pgAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-accounts/{id}")
    public ResponseEntity<Void> deletePgAccount(@PathVariable Long id) {
        log.debug("REST request to delete PgAccount : {}", id);
        pgAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
