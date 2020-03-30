package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgTransactionType2Service;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PgTransactionType2DTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PgTransactionType2}.
 */
@RestController
@RequestMapping("/api")
public class PgTransactionType2Resource {

    private final Logger log = LoggerFactory.getLogger(PgTransactionType2Resource.class);

    private static final String ENTITY_NAME = "pgTransactionType2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgTransactionType2Service pgTransactionType2Service;

    public PgTransactionType2Resource(PgTransactionType2Service pgTransactionType2Service) {
        this.pgTransactionType2Service = pgTransactionType2Service;
    }

    /**
     * {@code POST  /pg-transaction-type-2-s} : Create a new pgTransactionType2.
     *
     * @param pgTransactionType2DTO the pgTransactionType2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgTransactionType2DTO, or with status {@code 400 (Bad Request)} if the pgTransactionType2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pg-transaction-type-2-s")
    public ResponseEntity<PgTransactionType2DTO> createPgTransactionType2(@Valid @RequestBody PgTransactionType2DTO pgTransactionType2DTO) throws URISyntaxException {
        log.debug("REST request to save PgTransactionType2 : {}", pgTransactionType2DTO);
        if (pgTransactionType2DTO.getId() != null) {
            throw new BadRequestAlertException("A new pgTransactionType2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PgTransactionType2DTO result = pgTransactionType2Service.save(pgTransactionType2DTO);
        return ResponseEntity.created(new URI("/api/pg-transaction-type-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pg-transaction-type-2-s} : Updates an existing pgTransactionType2.
     *
     * @param pgTransactionType2DTO the pgTransactionType2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgTransactionType2DTO,
     * or with status {@code 400 (Bad Request)} if the pgTransactionType2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgTransactionType2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pg-transaction-type-2-s")
    public ResponseEntity<PgTransactionType2DTO> updatePgTransactionType2(@Valid @RequestBody PgTransactionType2DTO pgTransactionType2DTO) throws URISyntaxException {
        log.debug("REST request to update PgTransactionType2 : {}", pgTransactionType2DTO);
        if (pgTransactionType2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PgTransactionType2DTO result = pgTransactionType2Service.save(pgTransactionType2DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pgTransactionType2DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pg-transaction-type-2-s} : get all the pgTransactionType2S.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgTransactionType2S in body.
     */
    @GetMapping("/pg-transaction-type-2-s")
    public List<PgTransactionType2DTO> getAllPgTransactionType2S() {
        log.debug("REST request to get all PgTransactionType2S");
        return pgTransactionType2Service.findAll();
    }

    /**
     * {@code GET  /pg-transaction-type-2-s/:id} : get the "id" pgTransactionType2.
     *
     * @param id the id of the pgTransactionType2DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgTransactionType2DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pg-transaction-type-2-s/{id}")
    public ResponseEntity<PgTransactionType2DTO> getPgTransactionType2(@PathVariable Long id) {
        log.debug("REST request to get PgTransactionType2 : {}", id);
        Optional<PgTransactionType2DTO> pgTransactionType2DTO = pgTransactionType2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(pgTransactionType2DTO);
    }

    /**
     * {@code DELETE  /pg-transaction-type-2-s/:id} : delete the "id" pgTransactionType2.
     *
     * @param id the id of the pgTransactionType2DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pg-transaction-type-2-s/{id}")
    public ResponseEntity<Void> deletePgTransactionType2(@PathVariable Long id) {
        log.debug("REST request to delete PgTransactionType2 : {}", id);
        pgTransactionType2Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
