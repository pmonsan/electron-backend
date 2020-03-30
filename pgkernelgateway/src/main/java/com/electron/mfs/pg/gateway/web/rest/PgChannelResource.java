package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgChannelService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgChannelDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgChannel}.
 */
@RestController
@RequestMapping("/api")
public class PgChannelResource {

    private final Logger log = LoggerFactory.getLogger(PgChannelResource.class);

    private static final String ENTITY_NAME = "pgChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgChannelService pgChannelService;

    public PgChannelResource(PgChannelService pgChannelService) {
        this.pgChannelService = pgChannelService;
    }

    /**
     * {@code POST  /pg-channels} : Create a new pgChannel.
     *
     * @param pgChannelDTO the pgChannelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgChannelDTO, or with status {@code 400 (Bad Request)} if the pgChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-channels")
    public ResponseEntity<PgChannelDTO> createPgChannel(@Valid @RequestBody PgChannelDTO pgChannelDTO) throws URISyntaxException {
        log.debug("REST request to save PgChannel : {}", pgChannelDTO);
        if (pgChannelDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgChannelDTO result = pgChannelService.save(pgChannelDTO);
        return ResponseEntity.created(new URI("/api/pg-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-channels} : Updates an existing pgChannel.
     *
     * @param pgChannelDTO the pgChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgChannelDTO,
     * or with status {@code 400 (Bad Request)} if the pgChannelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-channels")
    public ResponseEntity<PgChannelDTO> updatePgChannel(@Valid @RequestBody PgChannelDTO pgChannelDTO) throws URISyntaxException {
        log.debug("REST request to update PgChannel : {}", pgChannelDTO);
        if (pgChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgChannelDTO result = pgChannelService.save(pgChannelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-channels} : get all the pgChannels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgChannels in body.
     */
    @GetMapping("/pg-channels")
    public List<PgChannelDTO> getAllPgChannels() {
        log.debug("REST request to get all PgChannels");
        return pgChannelService.findAll();
    }

    /**
     * {@code GET  /pg-channels/:id} : get the "id" pgChannel.
     *
     * @param id the id of the pgChannelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgChannelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-channels/{id}")
    public ResponseEntity<PgChannelDTO> getPgChannel(@PathVariable Long id) {
        log.debug("REST request to get PgChannel : {}", id);
        Optional<PgChannelDTO> pgChannelDTO = pgChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgChannelDTO);
    }

    /**
     * {@code DELETE  /pg-channels/:id} : delete the "id" pgChannel.
     *
     * @param id the id of the pgChannelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-channels/{id}")
    public ResponseEntity<Void> deletePgChannel(@PathVariable Long id) {
        log.debug("REST request to delete PgChannel : {}", id);
        pgChannelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
