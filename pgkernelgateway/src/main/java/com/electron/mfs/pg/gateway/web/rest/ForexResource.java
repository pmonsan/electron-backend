package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.ForexService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.ForexDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.Forex}.
 */
@RestController
@RequestMapping("/api")
public class ForexResource {

    private final Logger log = LoggerFactory.getLogger(ForexResource.class);

    private static final String ENTITY_NAME = "forex";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ForexService forexService;

    public ForexResource(ForexService forexService) {
        this.forexService = forexService;
    }

    /**
     * {@code POST  /forexes} : Create a new forex.
     *
     * @param forexDTO the forexDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new forexDTO, or with status {@code 400 (Bad Request)} if the forex has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forexes")
    public ResponseEntity<ForexDTO> createForex(@Valid @RequestBody ForexDTO forexDTO) throws URISyntaxException {
        log.debug("REST request to save Forex : {}", forexDTO);
        if (forexDTO.getId() != null) {
            throw new BadRequestAlertException("A new forex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ForexDTO result = forexService.save(forexDTO);
        return ResponseEntity.created(new URI("/api/forexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forexes} : Updates an existing forex.
     *
     * @param forexDTO the forexDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forexDTO,
     * or with status {@code 400 (Bad Request)} if the forexDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the forexDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forexes")
    public ResponseEntity<ForexDTO> updateForex(@Valid @RequestBody ForexDTO forexDTO) throws URISyntaxException {
        log.debug("REST request to update Forex : {}", forexDTO);
        if (forexDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ForexDTO result = forexService.save(forexDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, forexDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forexes} : get all the forexes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of forexes in body.
     */
    @GetMapping("/forexes")
    public List<ForexDTO> getAllForexes() {
        log.debug("REST request to get all Forexes");
        return forexService.findAll();
    }

    /**
     * {@code GET  /forexes/:id} : get the "id" forex.
     *
     * @param id the id of the forexDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the forexDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forexes/{id}")
    public ResponseEntity<ForexDTO> getForex(@PathVariable Long id) {
        log.debug("REST request to get Forex : {}", id);
        Optional<ForexDTO> forexDTO = forexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(forexDTO);
    }

    /**
     * {@code DELETE  /forexes/:id} : delete the "id" forex.
     *
     * @param id the id of the forexDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forexes/{id}")
    public ResponseEntity<Void> deleteForex(@PathVariable Long id) {
        log.debug("REST request to delete Forex : {}", id);
        forexService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
