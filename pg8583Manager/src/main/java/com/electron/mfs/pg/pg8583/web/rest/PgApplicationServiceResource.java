package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.service.PgApplicationServiceService;
import com.electron.mfs.pg.pg8583.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.pg8583.service.dto.PgApplicationServiceDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.pg8583.domain.PgApplicationService}.
 */
@RestController
@RequestMapping("/api")
public class PgApplicationServiceResource {

    private final Logger log = LoggerFactory.getLogger(PgApplicationServiceResource.class);

    private static final String ENTITY_NAME = "pg8583ManagerPgApplicationService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgApplicationServiceService pgApplicationServiceService;

    public PgApplicationServiceResource(PgApplicationServiceService pgApplicationServiceService) {
        this.pgApplicationServiceService = pgApplicationServiceService;
    }

    /**
     * {@code POST  /pg-application-services} : Create a new pgApplicationService.
     *
     * @param pgApplicationServiceDTO the pgApplicationServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgApplicationServiceDTO, or with status {@code 400 (Bad Request)} if the pgApplicationService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-application-services")
    public ResponseEntity<PgApplicationServiceDTO> createPgApplicationService(@Valid @RequestBody PgApplicationServiceDTO pgApplicationServiceDTO) throws URISyntaxException {
        log.debug("REST request to save PgApplicationService : {}", pgApplicationServiceDTO);
        if (pgApplicationServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgApplicationService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgApplicationServiceDTO result = pgApplicationServiceService.save(pgApplicationServiceDTO);
        return ResponseEntity.created(new URI("/api/pg-application-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-application-services} : Updates an existing pgApplicationService.
     *
     * @param pgApplicationServiceDTO the pgApplicationServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgApplicationServiceDTO,
     * or with status {@code 400 (Bad Request)} if the pgApplicationServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgApplicationServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-application-services")
    public ResponseEntity<PgApplicationServiceDTO> updatePgApplicationService(@Valid @RequestBody PgApplicationServiceDTO pgApplicationServiceDTO) throws URISyntaxException {
        log.debug("REST request to update PgApplicationService : {}", pgApplicationServiceDTO);
        if (pgApplicationServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgApplicationServiceDTO result = pgApplicationServiceService.save(pgApplicationServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgApplicationServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-application-services} : get all the pgApplicationServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgApplicationServices in body.
     */
    @GetMapping("/pg-application-services")
    public List<PgApplicationServiceDTO> getAllPgApplicationServices() {
        log.debug("REST request to get all PgApplicationServices");
        return pgApplicationServiceService.findAll();
    }

    /**
     * {@code GET  /pg-application-services/:id} : get the "id" pgApplicationService.
     *
     * @param id the id of the pgApplicationServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgApplicationServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-application-services/{id}")
    public ResponseEntity<PgApplicationServiceDTO> getPgApplicationService(@PathVariable Long id) {
        log.debug("REST request to get PgApplicationService : {}", id);
        Optional<PgApplicationServiceDTO> pgApplicationServiceDTO = pgApplicationServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgApplicationServiceDTO);
    }

    /**
     * {@code DELETE  /pg-application-services/:id} : delete the "id" pgApplicationService.
     *
     * @param id the id of the pgApplicationServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-application-services/{id}")
    public ResponseEntity<Void> deletePgApplicationService(@PathVariable Long id) {
        log.debug("REST request to delete PgApplicationService : {}", id);
        pgApplicationServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
