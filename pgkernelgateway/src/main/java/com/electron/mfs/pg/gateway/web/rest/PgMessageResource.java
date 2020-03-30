package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgMessageService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgMessageDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgMessage}.
 */
@RestController
@RequestMapping("/api")
public class PgMessageResource {

    private final Logger log = LoggerFactory.getLogger(PgMessageResource.class);

    private static final String ENTITY_NAME = "pgMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgMessageService pgMessageService;

    public PgMessageResource(PgMessageService pgMessageService) {
        this.pgMessageService = pgMessageService;
    }

    /**
     * {@code POST  /pg-messages} : Create a new pgMessage.
     *
     * @param pgMessageDTO the pgMessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgMessageDTO, or with status {@code 400 (Bad Request)} if the pgMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-messages")
    public ResponseEntity<PgMessageDTO> createPgMessage(@Valid @RequestBody PgMessageDTO pgMessageDTO) throws URISyntaxException {
        log.debug("REST request to save PgMessage : {}", pgMessageDTO);
        if (pgMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgMessageDTO result = pgMessageService.save(pgMessageDTO);
        return ResponseEntity.created(new URI("/api/pg-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-messages} : Updates an existing pgMessage.
     *
     * @param pgMessageDTO the pgMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgMessageDTO,
     * or with status {@code 400 (Bad Request)} if the pgMessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-messages")
    public ResponseEntity<PgMessageDTO> updatePgMessage(@Valid @RequestBody PgMessageDTO pgMessageDTO) throws URISyntaxException {
        log.debug("REST request to update PgMessage : {}", pgMessageDTO);
        if (pgMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgMessageDTO result = pgMessageService.save(pgMessageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-messages} : get all the pgMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgMessages in body.
     */
    @GetMapping("/pg-messages")
    public List<PgMessageDTO> getAllPgMessages() {
        log.debug("REST request to get all PgMessages");
        return pgMessageService.findAll();
    }

    /**
     * {@code GET  /pg-messages/:id} : get the "id" pgMessage.
     *
     * @param id the id of the pgMessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgMessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-messages/{id}")
    public ResponseEntity<PgMessageDTO> getPgMessage(@PathVariable Long id) {
        log.debug("REST request to get PgMessage : {}", id);
        Optional<PgMessageDTO> pgMessageDTO = pgMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgMessageDTO);
    }

    /**
     * {@code DELETE  /pg-messages/:id} : delete the "id" pgMessage.
     *
     * @param id the id of the pgMessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-messages/{id}")
    public ResponseEntity<Void> deletePgMessage(@PathVariable Long id) {
        log.debug("REST request to delete PgMessage : {}", id);
        pgMessageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
