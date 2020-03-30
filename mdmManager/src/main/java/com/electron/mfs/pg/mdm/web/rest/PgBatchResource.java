package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.PgBatchService;
import com.electron.mfs.pg.mdm.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.mdm.service.dto.PgBatchDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.mdm.domain.PgBatch}.
 */
@RestController
@RequestMapping("/api")
public class PgBatchResource {

    private final Logger log = LoggerFactory.getLogger(PgBatchResource.class);

    private static final String ENTITY_NAME = "mdmManagerPgBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgBatchService pgBatchService;

    public PgBatchResource(PgBatchService pgBatchService) {
        this.pgBatchService = pgBatchService;
    }

    /**
     * {@code POST  /pg-batches} : Create a new pgBatch.
     *
     * @param pgBatchDTO the pgBatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgBatchDTO, or with status {@code 400 (Bad Request)} if the pgBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-batches")
    public ResponseEntity<PgBatchDTO> createPgBatch(@Valid @RequestBody PgBatchDTO pgBatchDTO) throws URISyntaxException {
        log.debug("REST request to save PgBatch : {}", pgBatchDTO);
        if (pgBatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgBatchDTO result = pgBatchService.save(pgBatchDTO);
        return ResponseEntity.created(new URI("/api/pg-batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-batches} : Updates an existing pgBatch.
     *
     * @param pgBatchDTO the pgBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgBatchDTO,
     * or with status {@code 400 (Bad Request)} if the pgBatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-batches")
    public ResponseEntity<PgBatchDTO> updatePgBatch(@Valid @RequestBody PgBatchDTO pgBatchDTO) throws URISyntaxException {
        log.debug("REST request to update PgBatch : {}", pgBatchDTO);
        if (pgBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgBatchDTO result = pgBatchService.save(pgBatchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgBatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-batches} : get all the pgBatches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgBatches in body.
     */
    @GetMapping("/pg-batches")
    public List<PgBatchDTO> getAllPgBatches() {
        log.debug("REST request to get all PgBatches");
        return pgBatchService.findAll();
    }

    /**
     * {@code GET  /pg-batches/:id} : get the "id" pgBatch.
     *
     * @param id the id of the pgBatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgBatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-batches/{id}")
    public ResponseEntity<PgBatchDTO> getPgBatch(@PathVariable Long id) {
        log.debug("REST request to get PgBatch : {}", id);
        Optional<PgBatchDTO> pgBatchDTO = pgBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgBatchDTO);
    }

    /**
     * {@code DELETE  /pg-batches/:id} : delete the "id" pgBatch.
     *
     * @param id the id of the pgBatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-batches/{id}")
    public ResponseEntity<Void> deletePgBatch(@PathVariable Long id) {
        log.debug("REST request to delete PgBatch : {}", id);
        pgBatchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
