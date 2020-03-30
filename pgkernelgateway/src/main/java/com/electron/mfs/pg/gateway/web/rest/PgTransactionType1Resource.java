package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgTransactionType1Service;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgTransactionType1DTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgTransactionType1}.
 */
@RestController
@RequestMapping("/api")
public class PgTransactionType1Resource {

    private final Logger log = LoggerFactory.getLogger(PgTransactionType1Resource.class);

    private static final String ENTITY_NAME = "pgTransactionType1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgTransactionType1Service pgTransactionType1Service;

    public PgTransactionType1Resource(PgTransactionType1Service pgTransactionType1Service) {
        this.pgTransactionType1Service = pgTransactionType1Service;
    }

    /**
     * {@code POST  /pg-transaction-type-1-s} : Create a new pgTransactionType1.
     *
     * @param pgTransactionType1DTO the pgTransactionType1DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgTransactionType1DTO, or with status {@code 400 (Bad Request)} if the pgTransactionType1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-transaction-type-1-s")
    public ResponseEntity<PgTransactionType1DTO> createPgTransactionType1(@Valid @RequestBody PgTransactionType1DTO pgTransactionType1DTO) throws URISyntaxException {
        log.debug("REST request to save PgTransactionType1 : {}", pgTransactionType1DTO);
        if (pgTransactionType1DTO.getId() != null) {
            throw new BadRequestAlertException("A new pgTransactionType1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgTransactionType1DTO result = pgTransactionType1Service.save(pgTransactionType1DTO);
        return ResponseEntity.created(new URI("/api/pg-transaction-type-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-transaction-type-1-s} : Updates an existing pgTransactionType1.
     *
     * @param pgTransactionType1DTO the pgTransactionType1DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgTransactionType1DTO,
     * or with status {@code 400 (Bad Request)} if the pgTransactionType1DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgTransactionType1DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-transaction-type-1-s")
    public ResponseEntity<PgTransactionType1DTO> updatePgTransactionType1(@Valid @RequestBody PgTransactionType1DTO pgTransactionType1DTO) throws URISyntaxException {
        log.debug("REST request to update PgTransactionType1 : {}", pgTransactionType1DTO);
        if (pgTransactionType1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgTransactionType1DTO result = pgTransactionType1Service.save(pgTransactionType1DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgTransactionType1DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-transaction-type-1-s} : get all the pgTransactionType1S.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgTransactionType1S in body.
     */
    @GetMapping("/pg-transaction-type-1-s")
    public List<PgTransactionType1DTO> getAllPgTransactionType1S() {
        log.debug("REST request to get all PgTransactionType1S");
        return pgTransactionType1Service.findAll();
    }

    /**
     * {@code GET  /pg-transaction-type-1-s/:id} : get the "id" pgTransactionType1.
     *
     * @param id the id of the pgTransactionType1DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgTransactionType1DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-transaction-type-1-s/{id}")
    public ResponseEntity<PgTransactionType1DTO> getPgTransactionType1(@PathVariable Long id) {
        log.debug("REST request to get PgTransactionType1 : {}", id);
        Optional<PgTransactionType1DTO> pgTransactionType1DTO = pgTransactionType1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgTransactionType1DTO);
    }

    /**
     * {@code DELETE  /pg-transaction-type-1-s/:id} : delete the "id" pgTransactionType1.
     *
     * @param id the id of the pgTransactionType1DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-transaction-type-1-s/{id}")
    public ResponseEntity<Void> deletePgTransactionType1(@PathVariable Long id) {
        log.debug("REST request to delete PgTransactionType1 : {}", id);
        pgTransactionType1Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
