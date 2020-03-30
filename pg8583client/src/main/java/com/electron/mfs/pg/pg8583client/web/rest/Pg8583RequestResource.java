package com.electron.mfs.pg.pg8583client.web.rest;

import com.electron.mfs.pg.pg8583client.domain.Pg8583Request;
import com.electron.mfs.pg.pg8583client.repository.Pg8583RequestRepository;
import com.electron.mfs.pg.pg8583client.web.rest.errors.BadRequestAlertException;

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

    private final Pg8583RequestRepository pg8583RequestRepository;

    public Pg8583RequestResource(Pg8583RequestRepository pg8583RequestRepository) {
        this.pg8583RequestRepository = pg8583RequestRepository;
    }

    /**
     * {@code POST  /pg-8583-requests} : Create a new pg8583Request.
     *
     * @param pg8583Request the pg8583Request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pg8583Request, or with status {@code 400 (Bad Request)} if the pg8583Request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-8583-requests")
    public ResponseEntity<Pg8583Request> createPg8583Request(@Valid @RequestBody Pg8583Request pg8583Request) throws URISyntaxException {
        log.debug("REST request to save Pg8583Request : {}", pg8583Request);
        if (pg8583Request.getId() != null) {
            throw new BadRequestAlertException("A new pg8583Request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pg8583Request result = pg8583RequestRepository.save(pg8583Request);
        return ResponseEntity.created(new URI("/api/pg-8583-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-8583-requests} : Updates an existing pg8583Request.
     *
     * @param pg8583Request the pg8583Request to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pg8583Request,
     * or with status {@code 400 (Bad Request)} if the pg8583Request is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pg8583Request couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-8583-requests")
    public ResponseEntity<Pg8583Request> updatePg8583Request(@Valid @RequestBody Pg8583Request pg8583Request) throws URISyntaxException {
        log.debug("REST request to update Pg8583Request : {}", pg8583Request);
        if (pg8583Request.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pg8583Request result = pg8583RequestRepository.save(pg8583Request);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pg8583Request.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-8583-requests} : get all the pg8583Requests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pg8583Requests in body.
     */
    @GetMapping("/pg-8583-requests")
    public List<Pg8583Request> getAllPg8583Requests() {
        log.debug("REST request to get all Pg8583Requests");
        return pg8583RequestRepository.findAll();
    }

    /**
     * {@code GET  /pg-8583-requests/:id} : get the "id" pg8583Request.
     *
     * @param id the id of the pg8583Request to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pg8583Request, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-8583-requests/{id}")
    public ResponseEntity<Pg8583Request> getPg8583Request(@PathVariable Long id) {
        log.debug("REST request to get Pg8583Request : {}", id);
        Optional<Pg8583Request> pg8583Request = pg8583RequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pg8583Request);
    }

    /**
     * {@code DELETE  /pg-8583-requests/:id} : delete the "id" pg8583Request.
     *
     * @param id the id of the pg8583Request to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-8583-requests/{id}")
    public ResponseEntity<Void> deletePg8583Request(@PathVariable Long id) {
        log.debug("REST request to delete Pg8583Request : {}", id);
        pg8583RequestRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
