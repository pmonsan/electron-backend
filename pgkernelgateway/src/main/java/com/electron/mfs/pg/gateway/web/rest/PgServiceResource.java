package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgServiceService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgServiceDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgService}.
 */
@RestController
@RequestMapping("/api")
public class PgServiceResource {

    private final Logger log = LoggerFactory.getLogger(PgServiceResource.class);

    private static final String ENTITY_NAME = "pgService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgServiceService pgServiceService;

    public PgServiceResource(PgServiceService pgServiceService) {
        this.pgServiceService = pgServiceService;
    }

    /**
     * {@code POST  /pg-services} : Create a new pgService.
     *
     * @param pgServiceDTO the pgServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgServiceDTO, or with status {@code 400 (Bad Request)} if the pgService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-services")
    public ResponseEntity<PgServiceDTO> createPgService(@Valid @RequestBody PgServiceDTO pgServiceDTO) throws URISyntaxException {
        log.debug("REST request to save PgService : {}", pgServiceDTO);
        if (pgServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgServiceDTO result = pgServiceService.save(pgServiceDTO);
        return ResponseEntity.created(new URI("/api/pg-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-services} : Updates an existing pgService.
     *
     * @param pgServiceDTO the pgServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgServiceDTO,
     * or with status {@code 400 (Bad Request)} if the pgServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-services")
    public ResponseEntity<PgServiceDTO> updatePgService(@Valid @RequestBody PgServiceDTO pgServiceDTO) throws URISyntaxException {
        log.debug("REST request to update PgService : {}", pgServiceDTO);
        if (pgServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgServiceDTO result = pgServiceService.save(pgServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-services} : get all the pgServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgServices in body.
     */
    @GetMapping("/pg-services")
    public List<PgServiceDTO> getAllPgServices() {
        log.debug("REST request to get all PgServices");
        return pgServiceService.findAll();
    }

    /**
     * {@code GET  /pg-services/:id} : get the "id" pgService.
     *
     * @param id the id of the pgServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-services/{id}")
    public ResponseEntity<PgServiceDTO> getPgService(@PathVariable Long id) {
        log.debug("REST request to get PgService : {}", id);
        Optional<PgServiceDTO> pgServiceDTO = pgServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgServiceDTO);
    }

    /**
     * {@code DELETE  /pg-services/:id} : delete the "id" pgService.
     *
     * @param id the id of the pgServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-services/{id}")
    public ResponseEntity<Void> deletePgService(@PathVariable Long id) {
        log.debug("REST request to delete PgService : {}", id);
        pgServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
