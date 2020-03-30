package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.OperationStatusService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.OperationStatusDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.OperationStatus}.
 */
@RestController
@RequestMapping("/api")
public class OperationStatusResource {

    private final Logger log = LoggerFactory.getLogger(OperationStatusResource.class);

    private static final String ENTITY_NAME = "operationStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationStatusService operationStatusService;

    public OperationStatusResource(OperationStatusService operationStatusService) {
        this.operationStatusService = operationStatusService;
    }

    /**
     * {@code POST  /operation-statuses} : Create a new operationStatus.
     *
     * @param operationStatusDTO the operationStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationStatusDTO, or with status {@code 400 (Bad Request)} if the operationStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operation-statuses")
    public ResponseEntity<OperationStatusDTO> createOperationStatus(@Valid @RequestBody OperationStatusDTO operationStatusDTO) throws URISyntaxException {
        log.debug("REST request to save OperationStatus : {}", operationStatusDTO);
        if (operationStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new operationStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationStatusDTO result = operationStatusService.save(operationStatusDTO);
        return ResponseEntity.created(new URI("/api/operation-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operation-statuses} : Updates an existing operationStatus.
     *
     * @param operationStatusDTO the operationStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationStatusDTO,
     * or with status {@code 400 (Bad Request)} if the operationStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operation-statuses")
    public ResponseEntity<OperationStatusDTO> updateOperationStatus(@Valid @RequestBody OperationStatusDTO operationStatusDTO) throws URISyntaxException {
        log.debug("REST request to update OperationStatus : {}", operationStatusDTO);
        if (operationStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperationStatusDTO result = operationStatusService.save(operationStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /operation-statuses} : get all the operationStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationStatuses in body.
     */
    @GetMapping("/operation-statuses")
    public List<OperationStatusDTO> getAllOperationStatuses() {
        log.debug("REST request to get all OperationStatuses");
        return operationStatusService.findAll();
    }

    /**
     * {@code GET  /operation-statuses/:id} : get the "id" operationStatus.
     *
     * @param id the id of the operationStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operation-statuses/{id}")
    public ResponseEntity<OperationStatusDTO> getOperationStatus(@PathVariable Long id) {
        log.debug("REST request to get OperationStatus : {}", id);
        Optional<OperationStatusDTO> operationStatusDTO = operationStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationStatusDTO);
    }

    /**
     * {@code DELETE  /operation-statuses/:id} : delete the "id" operationStatus.
     *
     * @param id the id of the operationStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operation-statuses/{id}")
    public ResponseEntity<Void> deleteOperationStatus(@PathVariable Long id) {
        log.debug("REST request to delete OperationStatus : {}", id);
        operationStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
