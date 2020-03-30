package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.service.PgApplicationService;
import com.electron.mfs.pg.pg8583.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.pg8583.service.dto.PgApplicationDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.pg8583.domain.PgApplication}.
 */
@RestController
@RequestMapping("/api")
public class PgApplicationResource {

    private final Logger log = LoggerFactory.getLogger(PgApplicationResource.class);

    private static final String ENTITY_NAME = "pg8583ManagerPgApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgApplicationService pgApplicationService;

    public PgApplicationResource(PgApplicationService pgApplicationService) {
        this.pgApplicationService = pgApplicationService;
    }

    /**
     * {@code POST  /pg-applications} : Create a new pgApplication.
     *
     * @param pgApplicationDTO the pgApplicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgApplicationDTO, or with status {@code 400 (Bad Request)} if the pgApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-applications")
    public ResponseEntity<PgApplicationDTO> createPgApplication(@Valid @RequestBody PgApplicationDTO pgApplicationDTO) throws URISyntaxException {
        log.debug("REST request to save PgApplication : {}", pgApplicationDTO);
        if (pgApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgApplicationDTO result = pgApplicationService.save(pgApplicationDTO);
        return ResponseEntity.created(new URI("/api/pg-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-applications} : Updates an existing pgApplication.
     *
     * @param pgApplicationDTO the pgApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the pgApplicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-applications")
    public ResponseEntity<PgApplicationDTO> updatePgApplication(@Valid @RequestBody PgApplicationDTO pgApplicationDTO) throws URISyntaxException {
        log.debug("REST request to update PgApplication : {}", pgApplicationDTO);
        if (pgApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgApplicationDTO result = pgApplicationService.save(pgApplicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-applications} : get all the pgApplications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgApplications in body.
     */
    @GetMapping("/pg-applications")
    public List<PgApplicationDTO> getAllPgApplications() {
        log.debug("REST request to get all PgApplications");
        return pgApplicationService.findAll();
    }

    /**
     * {@code GET  /pg-applications/:id} : get the "id" pgApplication.
     *
     * @param id the id of the pgApplicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgApplicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-applications/{id}")
    public ResponseEntity<PgApplicationDTO> getPgApplication(@PathVariable Long id) {
        log.debug("REST request to get PgApplication : {}", id);
        Optional<PgApplicationDTO> pgApplicationDTO = pgApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgApplicationDTO);
    }

    /**
     * {@code DELETE  /pg-applications/:id} : delete the "id" pgApplication.
     *
     * @param id the id of the pgApplicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-applications/{id}")
    public ResponseEntity<Void> deletePgApplication(@PathVariable Long id) {
        log.debug("REST request to delete PgApplication : {}", id);
        pgApplicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
