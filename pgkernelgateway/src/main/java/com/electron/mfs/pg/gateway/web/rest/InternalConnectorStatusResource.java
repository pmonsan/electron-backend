package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.InternalConnectorStatusService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.InternalConnectorStatusDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.InternalConnectorStatus}.
 */
@RestController
@RequestMapping("/api")
public class InternalConnectorStatusResource {

    private final Logger log = LoggerFactory.getLogger(InternalConnectorStatusResource.class);

    private static final String ENTITY_NAME = "internalConnectorStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InternalConnectorStatusService internalConnectorStatusService;

    public InternalConnectorStatusResource(InternalConnectorStatusService internalConnectorStatusService) {
        this.internalConnectorStatusService = internalConnectorStatusService;
    }

    /**
     * {@code POST  /internal-connector-statuses} : Create a new internalConnectorStatus.
     *
     * @param internalConnectorStatusDTO the internalConnectorStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new internalConnectorStatusDTO, or with status {@code 400 (Bad Request)} if the internalConnectorStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/internal-connector-statuses")
    public ResponseEntity<InternalConnectorStatusDTO> createInternalConnectorStatus(@Valid @RequestBody InternalConnectorStatusDTO internalConnectorStatusDTO) throws URISyntaxException {
        log.debug("REST request to save InternalConnectorStatus : {}", internalConnectorStatusDTO);
        if (internalConnectorStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new internalConnectorStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternalConnectorStatusDTO result = internalConnectorStatusService.save(internalConnectorStatusDTO);
        return ResponseEntity.created(new URI("/api/internal-connector-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /internal-connector-statuses} : Updates an existing internalConnectorStatus.
     *
     * @param internalConnectorStatusDTO the internalConnectorStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internalConnectorStatusDTO,
     * or with status {@code 400 (Bad Request)} if the internalConnectorStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the internalConnectorStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/internal-connector-statuses")
    public ResponseEntity<InternalConnectorStatusDTO> updateInternalConnectorStatus(@Valid @RequestBody InternalConnectorStatusDTO internalConnectorStatusDTO) throws URISyntaxException {
        log.debug("REST request to update InternalConnectorStatus : {}", internalConnectorStatusDTO);
        if (internalConnectorStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InternalConnectorStatusDTO result = internalConnectorStatusService.save(internalConnectorStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internalConnectorStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /internal-connector-statuses} : get all the internalConnectorStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of internalConnectorStatuses in body.
     */
    @GetMapping("/internal-connector-statuses")
    public List<InternalConnectorStatusDTO> getAllInternalConnectorStatuses() {
        log.debug("REST request to get all InternalConnectorStatuses");
        return internalConnectorStatusService.findAll();
    }

    /**
     * {@code GET  /internal-connector-statuses/:id} : get the "id" internalConnectorStatus.
     *
     * @param id the id of the internalConnectorStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the internalConnectorStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/internal-connector-statuses/{id}")
    public ResponseEntity<InternalConnectorStatusDTO> getInternalConnectorStatus(@PathVariable Long id) {
        log.debug("REST request to get InternalConnectorStatus : {}", id);
        Optional<InternalConnectorStatusDTO> internalConnectorStatusDTO = internalConnectorStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internalConnectorStatusDTO);
    }

    /**
     * {@code DELETE  /internal-connector-statuses/:id} : delete the "id" internalConnectorStatus.
     *
     * @param id the id of the internalConnectorStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/internal-connector-statuses/{id}")
    public ResponseEntity<Void> deleteInternalConnectorStatus(@PathVariable Long id) {
        log.debug("REST request to delete InternalConnectorStatus : {}", id);
        internalConnectorStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
