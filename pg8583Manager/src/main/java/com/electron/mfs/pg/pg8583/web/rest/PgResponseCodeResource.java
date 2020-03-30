package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.service.PgResponseCodeService;
import com.electron.mfs.pg.pg8583.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.pg8583.service.dto.PgResponseCodeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.pg8583.domain.PgResponseCode}.
 */
@RestController
@RequestMapping("/api")
public class PgResponseCodeResource {

    private final Logger log = LoggerFactory.getLogger(PgResponseCodeResource.class);

    private static final String ENTITY_NAME = "pg8583ManagerPgResponseCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgResponseCodeService pgResponseCodeService;

    public PgResponseCodeResource(PgResponseCodeService pgResponseCodeService) {
        this.pgResponseCodeService = pgResponseCodeService;
    }

    /**
     * {@code POST  /pg-response-codes} : Create a new pgResponseCode.
     *
     * @param pgResponseCodeDTO the pgResponseCodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgResponseCodeDTO, or with status {@code 400 (Bad Request)} if the pgResponseCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-response-codes")
    public ResponseEntity<PgResponseCodeDTO> createPgResponseCode(@Valid @RequestBody PgResponseCodeDTO pgResponseCodeDTO) throws URISyntaxException {
        log.debug("REST request to save PgResponseCode : {}", pgResponseCodeDTO);
        if (pgResponseCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgResponseCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgResponseCodeDTO result = pgResponseCodeService.save(pgResponseCodeDTO);
        return ResponseEntity.created(new URI("/api/pg-response-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-response-codes} : Updates an existing pgResponseCode.
     *
     * @param pgResponseCodeDTO the pgResponseCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgResponseCodeDTO,
     * or with status {@code 400 (Bad Request)} if the pgResponseCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgResponseCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-response-codes")
    public ResponseEntity<PgResponseCodeDTO> updatePgResponseCode(@Valid @RequestBody PgResponseCodeDTO pgResponseCodeDTO) throws URISyntaxException {
        log.debug("REST request to update PgResponseCode : {}", pgResponseCodeDTO);
        if (pgResponseCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgResponseCodeDTO result = pgResponseCodeService.save(pgResponseCodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgResponseCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-response-codes} : get all the pgResponseCodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgResponseCodes in body.
     */
    @GetMapping("/pg-response-codes")
    public List<PgResponseCodeDTO> getAllPgResponseCodes() {
        log.debug("REST request to get all PgResponseCodes");
        return pgResponseCodeService.findAll();
    }

    /**
     * {@code GET  /pg-response-codes/:id} : get the "id" pgResponseCode.
     *
     * @param id the id of the pgResponseCodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgResponseCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-response-codes/{id}")
    public ResponseEntity<PgResponseCodeDTO> getPgResponseCode(@PathVariable Long id) {
        log.debug("REST request to get PgResponseCode : {}", id);
        Optional<PgResponseCodeDTO> pgResponseCodeDTO = pgResponseCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgResponseCodeDTO);
    }

    /**
     * {@code DELETE  /pg-response-codes/:id} : delete the "id" pgResponseCode.
     *
     * @param id the id of the pgResponseCodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-response-codes/{id}")
    public ResponseEntity<Void> deletePgResponseCode(@PathVariable Long id) {
        log.debug("REST request to delete PgResponseCode : {}", id);
        pgResponseCodeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
