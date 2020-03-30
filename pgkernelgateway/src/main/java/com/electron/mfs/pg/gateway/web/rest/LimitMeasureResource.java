package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.LimitMeasureService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.LimitMeasureDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.LimitMeasure}.
 */
@RestController
@RequestMapping("/api")
public class LimitMeasureResource {

    private final Logger log = LoggerFactory.getLogger(LimitMeasureResource.class);

    private static final String ENTITY_NAME = "limitMeasure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LimitMeasureService limitMeasureService;

    public LimitMeasureResource(LimitMeasureService limitMeasureService) {
        this.limitMeasureService = limitMeasureService;
    }

    /**
     * {@code POST  /limit-measures} : Create a new limitMeasure.
     *
     * @param limitMeasureDTO the limitMeasureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new limitMeasureDTO, or with status {@code 400 (Bad Request)} if the limitMeasure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/limit-measures")
    public ResponseEntity<LimitMeasureDTO> createLimitMeasure(@Valid @RequestBody LimitMeasureDTO limitMeasureDTO) throws URISyntaxException {
        log.debug("REST request to save LimitMeasure : {}", limitMeasureDTO);
        if (limitMeasureDTO.getId() != null) {
            throw new BadRequestAlertException("A new limitMeasure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LimitMeasureDTO result = limitMeasureService.save(limitMeasureDTO);
        return ResponseEntity.created(new URI("/api/limit-measures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /limit-measures} : Updates an existing limitMeasure.
     *
     * @param limitMeasureDTO the limitMeasureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated limitMeasureDTO,
     * or with status {@code 400 (Bad Request)} if the limitMeasureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the limitMeasureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/limit-measures")
    public ResponseEntity<LimitMeasureDTO> updateLimitMeasure(@Valid @RequestBody LimitMeasureDTO limitMeasureDTO) throws URISyntaxException {
        log.debug("REST request to update LimitMeasure : {}", limitMeasureDTO);
        if (limitMeasureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LimitMeasureDTO result = limitMeasureService.save(limitMeasureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, limitMeasureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /limit-measures} : get all the limitMeasures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of limitMeasures in body.
     */
    @GetMapping("/limit-measures")
    public List<LimitMeasureDTO> getAllLimitMeasures() {
        log.debug("REST request to get all LimitMeasures");
        return limitMeasureService.findAll();
    }

    /**
     * {@code GET  /limit-measures/:id} : get the "id" limitMeasure.
     *
     * @param id the id of the limitMeasureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the limitMeasureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/limit-measures/{id}")
    public ResponseEntity<LimitMeasureDTO> getLimitMeasure(@PathVariable Long id) {
        log.debug("REST request to get LimitMeasure : {}", id);
        Optional<LimitMeasureDTO> limitMeasureDTO = limitMeasureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(limitMeasureDTO);
    }

    /**
     * {@code DELETE  /limit-measures/:id} : delete the "id" limitMeasure.
     *
     * @param id the id of the limitMeasureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/limit-measures/{id}")
    public ResponseEntity<Void> deleteLimitMeasure(@PathVariable Long id) {
        log.debug("REST request to delete LimitMeasure : {}", id);
        limitMeasureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
