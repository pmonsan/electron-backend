package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgDataService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgDataDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgData}.
 */
@RestController
@RequestMapping("/api")
public class PgDataResource {

    private final Logger log = LoggerFactory.getLogger(PgDataResource.class);

    private static final String ENTITY_NAME = "pgData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgDataService pgDataService;

    public PgDataResource(PgDataService pgDataService) {
        this.pgDataService = pgDataService;
    }

    /**
     * {@code POST  /pg-data} : Create a new pgData.
     *
     * @param pgDataDTO the pgDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgDataDTO, or with status {@code 400 (Bad Request)} if the pgData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-data")
    public ResponseEntity<PgDataDTO> createPgData(@Valid @RequestBody PgDataDTO pgDataDTO) throws URISyntaxException {
        log.debug("REST request to save PgData : {}", pgDataDTO);
        if (pgDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgDataDTO result = pgDataService.save(pgDataDTO);
        return ResponseEntity.created(new URI("/api/pg-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-data} : Updates an existing pgData.
     *
     * @param pgDataDTO the pgDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgDataDTO,
     * or with status {@code 400 (Bad Request)} if the pgDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-data")
    public ResponseEntity<PgDataDTO> updatePgData(@Valid @RequestBody PgDataDTO pgDataDTO) throws URISyntaxException {
        log.debug("REST request to update PgData : {}", pgDataDTO);
        if (pgDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgDataDTO result = pgDataService.save(pgDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-data} : get all the pgData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgData in body.
     */
    @GetMapping("/pg-data")
    public List<PgDataDTO> getAllPgData() {
        log.debug("REST request to get all PgData");
        return pgDataService.findAll();
    }

    /**
     * {@code GET  /pg-data/:id} : get the "id" pgData.
     *
     * @param id the id of the pgDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-data/{id}")
    public ResponseEntity<PgDataDTO> getPgData(@PathVariable Long id) {
        log.debug("REST request to get PgData : {}", id);
        Optional<PgDataDTO> pgDataDTO = pgDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgDataDTO);
    }

    /**
     * {@code DELETE  /pg-data/:id} : delete the "id" pgData.
     *
     * @param id the id of the pgDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-data/{id}")
    public ResponseEntity<Void> deletePgData(@PathVariable Long id) {
        log.debug("REST request to delete PgData : {}", id);
        pgDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
