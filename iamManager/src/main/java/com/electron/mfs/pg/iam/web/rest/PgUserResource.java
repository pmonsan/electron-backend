package com.electron.mfs.pg.iam.web.rest;

import com.electron.mfs.pg.iam.service.PgUserService;
import com.electron.mfs.pg.iam.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.iam.service.dto.PgUserDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.iam.domain.PgUser}.
 */
@RestController
@RequestMapping("/api")
public class PgUserResource {

    private final Logger log = LoggerFactory.getLogger(PgUserResource.class);

    private static final String ENTITY_NAME = "iamManagerPgUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgUserService pgUserService;

    public PgUserResource(PgUserService pgUserService) {
        this.pgUserService = pgUserService;
    }

    /**
     * {@code POST  /pg-users} : Create a new pgUser.
     *
     * @param pgUserDTO the pgUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgUserDTO, or with status {@code 400 (Bad Request)} if the pgUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-users")
    public ResponseEntity<PgUserDTO> createPgUser(@Valid @RequestBody PgUserDTO pgUserDTO) throws URISyntaxException {
        log.debug("REST request to save PgUser : {}", pgUserDTO);
        if (pgUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgUserDTO result = pgUserService.save(pgUserDTO);
        return ResponseEntity.created(new URI("/api/pg-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-users} : Updates an existing pgUser.
     *
     * @param pgUserDTO the pgUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgUserDTO,
     * or with status {@code 400 (Bad Request)} if the pgUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-users")
    public ResponseEntity<PgUserDTO> updatePgUser(@Valid @RequestBody PgUserDTO pgUserDTO) throws URISyntaxException {
        log.debug("REST request to update PgUser : {}", pgUserDTO);
        if (pgUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgUserDTO result = pgUserService.save(pgUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-users} : get all the pgUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgUsers in body.
     */
    @GetMapping("/pg-users")
    public List<PgUserDTO> getAllPgUsers() {
        log.debug("REST request to get all PgUsers");
        return pgUserService.findAll();
    }

    /**
     * {@code GET  /pg-users/:id} : get the "id" pgUser.
     *
     * @param id the id of the pgUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-users/{id}")
    public ResponseEntity<PgUserDTO> getPgUser(@PathVariable Long id) {
        log.debug("REST request to get PgUser : {}", id);
        Optional<PgUserDTO> pgUserDTO = pgUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgUserDTO);
    }

    /**
     * {@code DELETE  /pg-users/:id} : delete the "id" pgUser.
     *
     * @param id the id of the pgUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-users/{id}")
    public ResponseEntity<Void> deletePgUser(@PathVariable Long id) {
        log.debug("REST request to delete PgUser : {}", id);
        pgUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
