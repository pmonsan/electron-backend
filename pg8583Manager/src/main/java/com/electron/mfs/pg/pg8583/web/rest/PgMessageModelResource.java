package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.service.PgMessageModelService;
import com.electron.mfs.pg.pg8583.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageModelDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.pg8583.domain.PgMessageModel}.
 */
@RestController
@RequestMapping("/api")
public class PgMessageModelResource {

    private final Logger log = LoggerFactory.getLogger(PgMessageModelResource.class);

    private static final String ENTITY_NAME = "pg8583ManagerPgMessageModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgMessageModelService pgMessageModelService;

    public PgMessageModelResource(PgMessageModelService pgMessageModelService) {
        this.pgMessageModelService = pgMessageModelService;
    }

    /**
     * {@code POST  /pg-message-models} : Create a new pgMessageModel.
     *
     * @param pgMessageModelDTO the pgMessageModelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgMessageModelDTO, or with status {@code 400 (Bad Request)} if the pgMessageModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-message-models")
    public ResponseEntity<PgMessageModelDTO> createPgMessageModel(@Valid @RequestBody PgMessageModelDTO pgMessageModelDTO) throws URISyntaxException {
        log.debug("REST request to save PgMessageModel : {}", pgMessageModelDTO);
        if (pgMessageModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgMessageModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgMessageModelDTO result = pgMessageModelService.save(pgMessageModelDTO);
        return ResponseEntity.created(new URI("/api/pg-message-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-message-models} : Updates an existing pgMessageModel.
     *
     * @param pgMessageModelDTO the pgMessageModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgMessageModelDTO,
     * or with status {@code 400 (Bad Request)} if the pgMessageModelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgMessageModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-message-models")
    public ResponseEntity<PgMessageModelDTO> updatePgMessageModel(@Valid @RequestBody PgMessageModelDTO pgMessageModelDTO) throws URISyntaxException {
        log.debug("REST request to update PgMessageModel : {}", pgMessageModelDTO);
        if (pgMessageModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgMessageModelDTO result = pgMessageModelService.save(pgMessageModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgMessageModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-message-models} : get all the pgMessageModels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgMessageModels in body.
     */
    @GetMapping("/pg-message-models")
    public List<PgMessageModelDTO> getAllPgMessageModels() {
        log.debug("REST request to get all PgMessageModels");
        return pgMessageModelService.findAll();
    }

    /**
     * {@code GET  /pg-message-models/:id} : get the "id" pgMessageModel.
     *
     * @param id the id of the pgMessageModelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgMessageModelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-message-models/{id}")
    public ResponseEntity<PgMessageModelDTO> getPgMessageModel(@PathVariable Long id) {
        log.debug("REST request to get PgMessageModel : {}", id);
        Optional<PgMessageModelDTO> pgMessageModelDTO = pgMessageModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgMessageModelDTO);
    }

    /**
     * {@code DELETE  /pg-message-models/:id} : delete the "id" pgMessageModel.
     *
     * @param id the id of the pgMessageModelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-message-models/{id}")
    public ResponseEntity<Void> deletePgMessageModel(@PathVariable Long id) {
        log.debug("REST request to delete PgMessageModel : {}", id);
        pgMessageModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
