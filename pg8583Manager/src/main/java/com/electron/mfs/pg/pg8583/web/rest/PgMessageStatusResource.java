package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.service.PgMessageStatusService;
import com.electron.mfs.pg.pg8583.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageStatusDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.pg8583.domain.PgMessageStatus}.
 */
@RestController
@RequestMapping("/api")
public class PgMessageStatusResource {

    private final Logger log = LoggerFactory.getLogger(PgMessageStatusResource.class);

    private static final String ENTITY_NAME = "pg8583ManagerPgMessageStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgMessageStatusService pgMessageStatusService;

    public PgMessageStatusResource(PgMessageStatusService pgMessageStatusService) {
        this.pgMessageStatusService = pgMessageStatusService;
    }

    /**
     * {@code POST  /pg-message-statuses} : Create a new pgMessageStatus.
     *
     * @param pgMessageStatusDTO the pgMessageStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgMessageStatusDTO, or with status {@code 400 (Bad Request)} if the pgMessageStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-message-statuses")
    public ResponseEntity<PgMessageStatusDTO> createPgMessageStatus(@Valid @RequestBody PgMessageStatusDTO pgMessageStatusDTO) throws URISyntaxException {
        log.debug("REST request to save PgMessageStatus : {}", pgMessageStatusDTO);
        if (pgMessageStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgMessageStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgMessageStatusDTO result = pgMessageStatusService.save(pgMessageStatusDTO);
        return ResponseEntity.created(new URI("/api/pg-message-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-message-statuses} : Updates an existing pgMessageStatus.
     *
     * @param pgMessageStatusDTO the pgMessageStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgMessageStatusDTO,
     * or with status {@code 400 (Bad Request)} if the pgMessageStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgMessageStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-message-statuses")
    public ResponseEntity<PgMessageStatusDTO> updatePgMessageStatus(@Valid @RequestBody PgMessageStatusDTO pgMessageStatusDTO) throws URISyntaxException {
        log.debug("REST request to update PgMessageStatus : {}", pgMessageStatusDTO);
        if (pgMessageStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgMessageStatusDTO result = pgMessageStatusService.save(pgMessageStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgMessageStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-message-statuses} : get all the pgMessageStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgMessageStatuses in body.
     */
    @GetMapping("/pg-message-statuses")
    public List<PgMessageStatusDTO> getAllPgMessageStatuses() {
        log.debug("REST request to get all PgMessageStatuses");
        return pgMessageStatusService.findAll();
    }

    /**
     * {@code GET  /pg-message-statuses/:id} : get the "id" pgMessageStatus.
     *
     * @param id the id of the pgMessageStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgMessageStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-message-statuses/{id}")
    public ResponseEntity<PgMessageStatusDTO> getPgMessageStatus(@PathVariable Long id) {
        log.debug("REST request to get PgMessageStatus : {}", id);
        Optional<PgMessageStatusDTO> pgMessageStatusDTO = pgMessageStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgMessageStatusDTO);
    }

    /**
     * {@code DELETE  /pg-message-statuses/:id} : delete the "id" pgMessageStatus.
     *
     * @param id the id of the pgMessageStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-message-statuses/{id}")
    public ResponseEntity<Void> deletePgMessageStatus(@PathVariable Long id) {
        log.debug("REST request to delete PgMessageStatus : {}", id);
        pgMessageStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
