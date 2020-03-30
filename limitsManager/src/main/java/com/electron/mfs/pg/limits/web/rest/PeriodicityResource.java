package com.electron.mfs.pg.limits.web.rest;

import com.electron.mfs.pg.limits.service.PeriodicityService;
import com.electron.mfs.pg.limits.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.limits.service.dto.PeriodicityDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.limits.domain.Periodicity}.
 */
@RestController
@RequestMapping("/api")
public class PeriodicityResource {

    private final Logger log = LoggerFactory.getLogger(PeriodicityResource.class);

    private static final String ENTITY_NAME = "limitsManagerPeriodicity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodicityService periodicityService;

    public PeriodicityResource(PeriodicityService periodicityService) {
        this.periodicityService = periodicityService;
    }

    /**
     * {@code POST  /periodicities} : Create a new periodicity.
     *
     * @param periodicityDTO the periodicityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodicityDTO, or with status {@code 400 (Bad Request)} if the periodicity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodicities")
    public ResponseEntity<PeriodicityDTO> createPeriodicity(@Valid @RequestBody PeriodicityDTO periodicityDTO) throws URISyntaxException {
        log.debug("REST request to save Periodicity : {}", periodicityDTO);
        if (periodicityDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodicity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodicityDTO result = periodicityService.save(periodicityDTO);
        return ResponseEntity.created(new URI("/api/periodicities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodicities} : Updates an existing periodicity.
     *
     * @param periodicityDTO the periodicityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodicityDTO,
     * or with status {@code 400 (Bad Request)} if the periodicityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodicityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodicities")
    public ResponseEntity<PeriodicityDTO> updatePeriodicity(@Valid @RequestBody PeriodicityDTO periodicityDTO) throws URISyntaxException {
        log.debug("REST request to update Periodicity : {}", periodicityDTO);
        if (periodicityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodicityDTO result = periodicityService.save(periodicityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodicityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /periodicities} : get all the periodicities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodicities in body.
     */
    @GetMapping("/periodicities")
    public List<PeriodicityDTO> getAllPeriodicities() {
        log.debug("REST request to get all Periodicities");
        return periodicityService.findAll();
    }

    /**
     * {@code GET  /periodicities/:id} : get the "id" periodicity.
     *
     * @param id the id of the periodicityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodicityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodicities/{id}")
    public ResponseEntity<PeriodicityDTO> getPeriodicity(@PathVariable Long id) {
        log.debug("REST request to get Periodicity : {}", id);
        Optional<PeriodicityDTO> periodicityDTO = periodicityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodicityDTO);
    }

    /**
     * {@code DELETE  /periodicities/:id} : delete the "id" periodicity.
     *
     * @param id the id of the periodicityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodicities/{id}")
    public ResponseEntity<Void> deletePeriodicity(@PathVariable Long id) {
        log.debug("REST request to delete Periodicity : {}", id);
        periodicityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
