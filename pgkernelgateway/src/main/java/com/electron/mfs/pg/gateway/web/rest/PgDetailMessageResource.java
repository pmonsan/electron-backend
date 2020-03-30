package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgDetailMessageService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgDetailMessageDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgDetailMessage}.
 */
@RestController
@RequestMapping("/api")
public class PgDetailMessageResource {

    private final Logger log = LoggerFactory.getLogger(PgDetailMessageResource.class);

    private static final String ENTITY_NAME = "pgDetailMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgDetailMessageService pgDetailMessageService;

    public PgDetailMessageResource(PgDetailMessageService pgDetailMessageService) {
        this.pgDetailMessageService = pgDetailMessageService;
    }

    /**
     * {@code POST  /pg-detail-messages} : Create a new pgDetailMessage.
     *
     * @param pgDetailMessageDTO the pgDetailMessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgDetailMessageDTO, or with status {@code 400 (Bad Request)} if the pgDetailMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-detail-messages")
    public ResponseEntity<PgDetailMessageDTO> createPgDetailMessage(@Valid @RequestBody PgDetailMessageDTO pgDetailMessageDTO) throws URISyntaxException {
        log.debug("REST request to save PgDetailMessage : {}", pgDetailMessageDTO);
        if (pgDetailMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgDetailMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgDetailMessageDTO result = pgDetailMessageService.save(pgDetailMessageDTO);
        return ResponseEntity.created(new URI("/api/pg-detail-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-detail-messages} : Updates an existing pgDetailMessage.
     *
     * @param pgDetailMessageDTO the pgDetailMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgDetailMessageDTO,
     * or with status {@code 400 (Bad Request)} if the pgDetailMessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgDetailMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-detail-messages")
    public ResponseEntity<PgDetailMessageDTO> updatePgDetailMessage(@Valid @RequestBody PgDetailMessageDTO pgDetailMessageDTO) throws URISyntaxException {
        log.debug("REST request to update PgDetailMessage : {}", pgDetailMessageDTO);
        if (pgDetailMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgDetailMessageDTO result = pgDetailMessageService.save(pgDetailMessageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgDetailMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-detail-messages} : get all the pgDetailMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgDetailMessages in body.
     */
    @GetMapping("/pg-detail-messages")
    public List<PgDetailMessageDTO> getAllPgDetailMessages() {
        log.debug("REST request to get all PgDetailMessages");
        return pgDetailMessageService.findAll();
    }

    /**
     * {@code GET  /pg-detail-messages/:id} : get the "id" pgDetailMessage.
     *
     * @param id the id of the pgDetailMessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgDetailMessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-detail-messages/{id}")
    public ResponseEntity<PgDetailMessageDTO> getPgDetailMessage(@PathVariable Long id) {
        log.debug("REST request to get PgDetailMessage : {}", id);
        Optional<PgDetailMessageDTO> pgDetailMessageDTO = pgDetailMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgDetailMessageDTO);
    }

    /**
     * {@code DELETE  /pg-detail-messages/:id} : delete the "id" pgDetailMessage.
     *
     * @param id the id of the pgDetailMessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-detail-messages/{id}")
    public ResponseEntity<Void> deletePgDetailMessage(@PathVariable Long id) {
        log.debug("REST request to delete PgDetailMessage : {}", id);
        pgDetailMessageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
