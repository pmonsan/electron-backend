package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.service.PgMessageModelDataService;
import com.electron.mfs.pg.pg8583.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageModelDataDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.pg8583.domain.PgMessageModelData}.
 */
@RestController
@RequestMapping("/api")
public class PgMessageModelDataResource {

    private final Logger log = LoggerFactory.getLogger(PgMessageModelDataResource.class);

    private static final String ENTITY_NAME = "pg8583ManagerPgMessageModelData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgMessageModelDataService pgMessageModelDataService;

    public PgMessageModelDataResource(PgMessageModelDataService pgMessageModelDataService) {
        this.pgMessageModelDataService = pgMessageModelDataService;
    }

    /**
     * {@code POST  /pg-message-model-data} : Create a new pgMessageModelData.
     *
     * @param pgMessageModelDataDTO the pgMessageModelDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgMessageModelDataDTO, or with status {@code 400 (Bad Request)} if the pgMessageModelData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-message-model-data")
    public ResponseEntity<PgMessageModelDataDTO> createPgMessageModelData(@Valid @RequestBody PgMessageModelDataDTO pgMessageModelDataDTO) throws URISyntaxException {
        log.debug("REST request to save PgMessageModelData : {}", pgMessageModelDataDTO);
        if (pgMessageModelDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgMessageModelData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgMessageModelDataDTO result = pgMessageModelDataService.save(pgMessageModelDataDTO);
        return ResponseEntity.created(new URI("/api/pg-message-model-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-message-model-data} : Updates an existing pgMessageModelData.
     *
     * @param pgMessageModelDataDTO the pgMessageModelDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgMessageModelDataDTO,
     * or with status {@code 400 (Bad Request)} if the pgMessageModelDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgMessageModelDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-message-model-data")
    public ResponseEntity<PgMessageModelDataDTO> updatePgMessageModelData(@Valid @RequestBody PgMessageModelDataDTO pgMessageModelDataDTO) throws URISyntaxException {
        log.debug("REST request to update PgMessageModelData : {}", pgMessageModelDataDTO);
        if (pgMessageModelDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgMessageModelDataDTO result = pgMessageModelDataService.save(pgMessageModelDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgMessageModelDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-message-model-data} : get all the pgMessageModelData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgMessageModelData in body.
     */
    @GetMapping("/pg-message-model-data")
    public List<PgMessageModelDataDTO> getAllPgMessageModelData() {
        log.debug("REST request to get all PgMessageModelData");
        return pgMessageModelDataService.findAll();
    }

    /**
     * {@code GET  /pg-message-model-data/:id} : get the "id" pgMessageModelData.
     *
     * @param id the id of the pgMessageModelDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgMessageModelDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-message-model-data/{id}")
    public ResponseEntity<PgMessageModelDataDTO> getPgMessageModelData(@PathVariable Long id) {
        log.debug("REST request to get PgMessageModelData : {}", id);
        Optional<PgMessageModelDataDTO> pgMessageModelDataDTO = pgMessageModelDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgMessageModelDataDTO);
    }

    /**
     * {@code DELETE  /pg-message-model-data/:id} : delete the "id" pgMessageModelData.
     *
     * @param id the id of the pgMessageModelDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-message-model-data/{id}")
    public ResponseEntity<Void> deletePgMessageModelData(@PathVariable Long id) {
        log.debug("REST request to delete PgMessageModelData : {}", id);
        pgMessageModelDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
