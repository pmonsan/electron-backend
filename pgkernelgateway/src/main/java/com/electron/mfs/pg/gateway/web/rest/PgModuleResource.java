package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgModuleService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgModuleDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgModule}.
 */
@RestController
@RequestMapping("/api")
public class PgModuleResource {

    private final Logger log = LoggerFactory.getLogger(PgModuleResource.class);

    private static final String ENTITY_NAME = "pgModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgModuleService pgModuleService;

    public PgModuleResource(PgModuleService pgModuleService) {
        this.pgModuleService = pgModuleService;
    }

    /**
     * {@code POST  /pg-modules} : Create a new pgModule.
     *
     * @param pgModuleDTO the pgModuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgModuleDTO, or with status {@code 400 (Bad Request)} if the pgModule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-modules")
    public ResponseEntity<PgModuleDTO> createPgModule(@Valid @RequestBody PgModuleDTO pgModuleDTO) throws URISyntaxException {
        log.debug("REST request to save PgModule : {}", pgModuleDTO);
        if (pgModuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgModuleDTO result = pgModuleService.save(pgModuleDTO);
        return ResponseEntity.created(new URI("/api/pg-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-modules} : Updates an existing pgModule.
     *
     * @param pgModuleDTO the pgModuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgModuleDTO,
     * or with status {@code 400 (Bad Request)} if the pgModuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgModuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-modules")
    public ResponseEntity<PgModuleDTO> updatePgModule(@Valid @RequestBody PgModuleDTO pgModuleDTO) throws URISyntaxException {
        log.debug("REST request to update PgModule : {}", pgModuleDTO);
        if (pgModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgModuleDTO result = pgModuleService.save(pgModuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgModuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-modules} : get all the pgModules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgModules in body.
     */
    @GetMapping("/pg-modules")
    public List<PgModuleDTO> getAllPgModules() {
        log.debug("REST request to get all PgModules");
        return pgModuleService.findAll();
    }

    /**
     * {@code GET  /pg-modules/:id} : get the "id" pgModule.
     *
     * @param id the id of the pgModuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgModuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-modules/{id}")
    public ResponseEntity<PgModuleDTO> getPgModule(@PathVariable Long id) {
        log.debug("REST request to get PgModule : {}", id);
        Optional<PgModuleDTO> pgModuleDTO = pgModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgModuleDTO);
    }

    /**
     * {@code DELETE  /pg-modules/:id} : delete the "id" pgModule.
     *
     * @param id the id of the pgModuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-modules/{id}")
    public ResponseEntity<Void> deletePgModule(@PathVariable Long id) {
        log.debug("REST request to delete PgModule : {}", id);
        pgModuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
