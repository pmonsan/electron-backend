package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.ActivityAreaService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.ActivityAreaDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.ActivityArea}.
 */
@RestController
@RequestMapping("/api")
public class ActivityAreaResource {

    private final Logger log = LoggerFactory.getLogger(ActivityAreaResource.class);

    private static final String ENTITY_NAME = "activityArea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityAreaService activityAreaService;

    public ActivityAreaResource(ActivityAreaService activityAreaService) {
        this.activityAreaService = activityAreaService;
    }

    /**
     * {@code POST  /activity-areas} : Create a new activityArea.
     *
     * @param activityAreaDTO the activityAreaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityAreaDTO, or with status {@code 400 (Bad Request)} if the activityArea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-areas")
    public ResponseEntity<ActivityAreaDTO> createActivityArea(@Valid @RequestBody ActivityAreaDTO activityAreaDTO) throws URISyntaxException {
        log.debug("REST request to save ActivityArea : {}", activityAreaDTO);
        if (activityAreaDTO.getId() != null) {
            throw new BadRequestAlertException("A new activityArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityAreaDTO result = activityAreaService.save(activityAreaDTO);
        return ResponseEntity.created(new URI("/api/activity-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-areas} : Updates an existing activityArea.
     *
     * @param activityAreaDTO the activityAreaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityAreaDTO,
     * or with status {@code 400 (Bad Request)} if the activityAreaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityAreaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-areas")
    public ResponseEntity<ActivityAreaDTO> updateActivityArea(@Valid @RequestBody ActivityAreaDTO activityAreaDTO) throws URISyntaxException {
        log.debug("REST request to update ActivityArea : {}", activityAreaDTO);
        if (activityAreaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityAreaDTO result = activityAreaService.save(activityAreaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityAreaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /activity-areas} : get all the activityAreas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityAreas in body.
     */
    @GetMapping("/activity-areas")
    public List<ActivityAreaDTO> getAllActivityAreas() {
        log.debug("REST request to get all ActivityAreas");
        return activityAreaService.findAll();
    }

    /**
     * {@code GET  /activity-areas/:id} : get the "id" activityArea.
     *
     * @param id the id of the activityAreaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityAreaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-areas/{id}")
    public ResponseEntity<ActivityAreaDTO> getActivityArea(@PathVariable Long id) {
        log.debug("REST request to get ActivityArea : {}", id);
        Optional<ActivityAreaDTO> activityAreaDTO = activityAreaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityAreaDTO);
    }

    /**
     * {@code DELETE  /activity-areas/:id} : delete the "id" activityArea.
     *
     * @param id the id of the activityAreaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-areas/{id}")
    public ResponseEntity<Void> deleteActivityArea(@PathVariable Long id) {
        log.debug("REST request to delete ActivityArea : {}", id);
        activityAreaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
