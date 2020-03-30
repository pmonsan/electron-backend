package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgChannelAuthorizedService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgChannelAuthorizedDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgChannelAuthorized}.
 */
@RestController
@RequestMapping("/api")
public class PgChannelAuthorizedResource {

    private final Logger log = LoggerFactory.getLogger(PgChannelAuthorizedResource.class);

    private static final String ENTITY_NAME = "pgChannelAuthorized";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgChannelAuthorizedService pgChannelAuthorizedService;

    public PgChannelAuthorizedResource(PgChannelAuthorizedService pgChannelAuthorizedService) {
        this.pgChannelAuthorizedService = pgChannelAuthorizedService;
    }

    /**
     * {@code POST  /pg-channel-authorizeds} : Create a new pgChannelAuthorized.
     *
     * @param pgChannelAuthorizedDTO the pgChannelAuthorizedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgChannelAuthorizedDTO, or with status {@code 400 (Bad Request)} if the pgChannelAuthorized has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-channel-authorizeds")
    public ResponseEntity<PgChannelAuthorizedDTO> createPgChannelAuthorized(@Valid @RequestBody PgChannelAuthorizedDTO pgChannelAuthorizedDTO) throws URISyntaxException {
        log.debug("REST request to save PgChannelAuthorized : {}", pgChannelAuthorizedDTO);
        if (pgChannelAuthorizedDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgChannelAuthorized cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgChannelAuthorizedDTO result = pgChannelAuthorizedService.save(pgChannelAuthorizedDTO);
        return ResponseEntity.created(new URI("/api/pg-channel-authorizeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-channel-authorizeds} : Updates an existing pgChannelAuthorized.
     *
     * @param pgChannelAuthorizedDTO the pgChannelAuthorizedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgChannelAuthorizedDTO,
     * or with status {@code 400 (Bad Request)} if the pgChannelAuthorizedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgChannelAuthorizedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-channel-authorizeds")
    public ResponseEntity<PgChannelAuthorizedDTO> updatePgChannelAuthorized(@Valid @RequestBody PgChannelAuthorizedDTO pgChannelAuthorizedDTO) throws URISyntaxException {
        log.debug("REST request to update PgChannelAuthorized : {}", pgChannelAuthorizedDTO);
        if (pgChannelAuthorizedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgChannelAuthorizedDTO result = pgChannelAuthorizedService.save(pgChannelAuthorizedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgChannelAuthorizedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-channel-authorizeds} : get all the pgChannelAuthorizeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgChannelAuthorizeds in body.
     */
    @GetMapping("/pg-channel-authorizeds")
    public List<PgChannelAuthorizedDTO> getAllPgChannelAuthorizeds() {
        log.debug("REST request to get all PgChannelAuthorizeds");
        return pgChannelAuthorizedService.findAll();
    }

    /**
     * {@code GET  /pg-channel-authorizeds/:id} : get the "id" pgChannelAuthorized.
     *
     * @param id the id of the pgChannelAuthorizedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgChannelAuthorizedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-channel-authorizeds/{id}")
    public ResponseEntity<PgChannelAuthorizedDTO> getPgChannelAuthorized(@PathVariable Long id) {
        log.debug("REST request to get PgChannelAuthorized : {}", id);
        Optional<PgChannelAuthorizedDTO> pgChannelAuthorizedDTO = pgChannelAuthorizedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgChannelAuthorizedDTO);
    }

    /**
     * {@code DELETE  /pg-channel-authorizeds/:id} : delete the "id" pgChannelAuthorized.
     *
     * @param id the id of the pgChannelAuthorizedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-channel-authorizeds/{id}")
    public ResponseEntity<Void> deletePgChannelAuthorized(@PathVariable Long id) {
        log.debug("REST request to delete PgChannelAuthorized : {}", id);
        pgChannelAuthorizedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
