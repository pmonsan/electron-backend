package com.electron.mfs.pg.pg8583client.web.rest;

import com.electron.mfs.pg.pg8583client.service.Pg8583RequestService;
import com.electron.mfs.pg.pg8583client.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.pg8583client.service.dto.Pg8583RequestDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.pg8583client.domain.Pg8583Request}.
 */
@RestController
@RequestMapping("/api")
public class Pg8583RequestResource {

    private final Logger log = LoggerFactory.getLogger(Pg8583RequestResource.class);

    private static final String ENTITY_NAME = "pg8583ClientPg8583Request";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Pg8583RequestService pg8583RequestService;

    public Pg8583RequestResource(Pg8583RequestService pg8583RequestService) {
        this.pg8583RequestService = pg8583RequestService;
    }

    /**
     * {@code POST  /pg-8583-requests} : Create a new pg8583Request.
     *
     * @param pg8583RequestDTO the pg8583RequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pg8583RequestDTO, or with status {@code 400 (Bad Request)} if the pg8583Request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-8583-requests")
    public ResponseEntity<Pg8583RequestDTO> createPg8583Request(@Valid @RequestBody Pg8583RequestDTO pg8583RequestDTO) throws URISyntaxException {
        log.debug("REST request to save Pg8583Request : {}", pg8583RequestDTO);
        if (pg8583RequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new pg8583Request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pg8583RequestDTO result = pg8583RequestService.save(pg8583RequestDTO);
        return ResponseEntity.created(new URI("/api/pg-8583-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-8583-requests} : Updates an existing pg8583Request.
     *
     * @param pg8583RequestDTO the pg8583RequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pg8583RequestDTO,
     * or with status {@code 400 (Bad Request)} if the pg8583RequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pg8583RequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-8583-requests")
    public ResponseEntity<Pg8583RequestDTO> updatePg8583Request(@Valid @RequestBody Pg8583RequestDTO pg8583RequestDTO) throws URISyntaxException {
        log.debug("REST request to update Pg8583Request : {}", pg8583RequestDTO);
        if (pg8583RequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pg8583RequestDTO result = pg8583RequestService.save(pg8583RequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pg8583RequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-8583-requests} : get all the pg8583Requests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pg8583Requests in body.
     */
    @GetMapping("/pg-8583-requests")
    public List<Pg8583RequestDTO> getAllPg8583Requests() {
        log.debug("REST request to get all Pg8583Requests");
        return pg8583RequestService.findAll();
    }

    /**
     * {@code GET  /pg-8583-requests/:id} : get the "id" pg8583Request.
     *
     * @param id the id of the pg8583RequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pg8583RequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-8583-requests/{id}")
    public ResponseEntity<Pg8583RequestDTO> getPg8583Request(@PathVariable Long id) {
        log.debug("REST request to get Pg8583Request : {}", id);
        Optional<Pg8583RequestDTO> pg8583RequestDTO = pg8583RequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pg8583RequestDTO);
    }

    /**
     * {@code DELETE  /pg-8583-requests/:id} : delete the "id" pg8583Request.
     *
     * @param id the id of the pg8583RequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-8583-requests/{id}")
    public ResponseEntity<Void> deletePg8583Request(@PathVariable Long id) {
        log.debug("REST request to delete Pg8583Request : {}", id);
        pg8583RequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
