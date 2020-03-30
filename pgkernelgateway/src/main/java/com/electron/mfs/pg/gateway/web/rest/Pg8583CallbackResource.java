package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.Pg8583CallbackService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.Pg8583CallbackDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.Pg8583Callback}.
 */
@RestController
@RequestMapping("/api")
public class Pg8583CallbackResource {

    private final Logger log = LoggerFactory.getLogger(Pg8583CallbackResource.class);

    private static final String ENTITY_NAME = "pg8583Callback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Pg8583CallbackService pg8583CallbackService;

    public Pg8583CallbackResource(Pg8583CallbackService pg8583CallbackService) {
        this.pg8583CallbackService = pg8583CallbackService;
    }

    /**
     * {@code POST  /pg-8583-callbacks} : Create a new pg8583Callback.
     *
     * @param pg8583CallbackDTO the pg8583CallbackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pg8583CallbackDTO, or with status {@code 400 (Bad Request)} if the pg8583Callback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-8583-callbacks")
    public ResponseEntity<Pg8583CallbackDTO> createPg8583Callback(@Valid @RequestBody Pg8583CallbackDTO pg8583CallbackDTO) throws URISyntaxException {
        log.debug("REST request to save Pg8583Callback : {}", pg8583CallbackDTO);
        if (pg8583CallbackDTO.getId() != null) {
            throw new BadRequestAlertException("A new pg8583Callback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pg8583CallbackDTO result = pg8583CallbackService.save(pg8583CallbackDTO);
        return ResponseEntity.created(new URI("/api/pg-8583-callbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-8583-callbacks} : Updates an existing pg8583Callback.
     *
     * @param pg8583CallbackDTO the pg8583CallbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pg8583CallbackDTO,
     * or with status {@code 400 (Bad Request)} if the pg8583CallbackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pg8583CallbackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-8583-callbacks")
    public ResponseEntity<Pg8583CallbackDTO> updatePg8583Callback(@Valid @RequestBody Pg8583CallbackDTO pg8583CallbackDTO) throws URISyntaxException {
        log.debug("REST request to update Pg8583Callback : {}", pg8583CallbackDTO);
        if (pg8583CallbackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pg8583CallbackDTO result = pg8583CallbackService.save(pg8583CallbackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pg8583CallbackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-8583-callbacks} : get all the pg8583Callbacks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pg8583Callbacks in body.
     */
    @GetMapping("/pg-8583-callbacks")
    public List<Pg8583CallbackDTO> getAllPg8583Callbacks() {
        log.debug("REST request to get all Pg8583Callbacks");
        return pg8583CallbackService.findAll();
    }

    /**
     * {@code GET  /pg-8583-callbacks/:id} : get the "id" pg8583Callback.
     *
     * @param id the id of the pg8583CallbackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pg8583CallbackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-8583-callbacks/{id}")
    public ResponseEntity<Pg8583CallbackDTO> getPg8583Callback(@PathVariable Long id) {
        log.debug("REST request to get Pg8583Callback : {}", id);
        Optional<Pg8583CallbackDTO> pg8583CallbackDTO = pg8583CallbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pg8583CallbackDTO);
    }

    /**
     * {@code DELETE  /pg-8583-callbacks/:id} : delete the "id" pg8583Callback.
     *
     * @param id the id of the pg8583CallbackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-8583-callbacks/{id}")
    public ResponseEntity<Void> deletePg8583Callback(@PathVariable Long id) {
        log.debug("REST request to delete Pg8583Callback : {}", id);
        pg8583CallbackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
