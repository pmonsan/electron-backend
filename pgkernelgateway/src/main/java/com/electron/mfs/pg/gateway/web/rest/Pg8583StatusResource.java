package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.Pg8583StatusService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.Pg8583StatusDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.Pg8583Status}.
 */
@RestController
@RequestMapping("/api")
public class Pg8583StatusResource {

    private final Logger log = LoggerFactory.getLogger(Pg8583StatusResource.class);

    private static final String ENTITY_NAME = "pg8583Status";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Pg8583StatusService pg8583StatusService;

    public Pg8583StatusResource(Pg8583StatusService pg8583StatusService) {
        this.pg8583StatusService = pg8583StatusService;
    }

    /**
     * {@code POST  /pg-8583-statuses} : Create a new pg8583Status.
     *
     * @param pg8583StatusDTO the pg8583StatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pg8583StatusDTO, or with status {@code 400 (Bad Request)} if the pg8583Status has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-8583-statuses")
    public ResponseEntity<Pg8583StatusDTO> createPg8583Status(@Valid @RequestBody Pg8583StatusDTO pg8583StatusDTO) throws URISyntaxException {
        log.debug("REST request to save Pg8583Status : {}", pg8583StatusDTO);
        if (pg8583StatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new pg8583Status cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pg8583StatusDTO result = pg8583StatusService.save(pg8583StatusDTO);
        return ResponseEntity.created(new URI("/api/pg-8583-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-8583-statuses} : Updates an existing pg8583Status.
     *
     * @param pg8583StatusDTO the pg8583StatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pg8583StatusDTO,
     * or with status {@code 400 (Bad Request)} if the pg8583StatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pg8583StatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-8583-statuses")
    public ResponseEntity<Pg8583StatusDTO> updatePg8583Status(@Valid @RequestBody Pg8583StatusDTO pg8583StatusDTO) throws URISyntaxException {
        log.debug("REST request to update Pg8583Status : {}", pg8583StatusDTO);
        if (pg8583StatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pg8583StatusDTO result = pg8583StatusService.save(pg8583StatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pg8583StatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-8583-statuses} : get all the pg8583Statuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pg8583Statuses in body.
     */
    @GetMapping("/pg-8583-statuses")
    public List<Pg8583StatusDTO> getAllPg8583Statuses() {
        log.debug("REST request to get all Pg8583Statuses");
        return pg8583StatusService.findAll();
    }

    /**
     * {@code GET  /pg-8583-statuses/:id} : get the "id" pg8583Status.
     *
     * @param id the id of the pg8583StatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pg8583StatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-8583-statuses/{id}")
    public ResponseEntity<Pg8583StatusDTO> getPg8583Status(@PathVariable Long id) {
        log.debug("REST request to get Pg8583Status : {}", id);
        Optional<Pg8583StatusDTO> pg8583StatusDTO = pg8583StatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pg8583StatusDTO);
    }

    /**
     * {@code DELETE  /pg-8583-statuses/:id} : delete the "id" pg8583Status.
     *
     * @param id the id of the pg8583StatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-8583-statuses/{id}")
    public ResponseEntity<Void> deletePg8583Status(@PathVariable Long id) {
        log.debug("REST request to delete Pg8583Status : {}", id);
        pg8583StatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
