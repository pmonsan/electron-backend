package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.OperationTypeService;
import com.electron.mfs.pg.mdm.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.mdm.service.dto.OperationTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.mdm.domain.OperationType}.
 */
@RestController
@RequestMapping("/api")
public class OperationTypeResource {

    private final Logger log = LoggerFactory.getLogger(OperationTypeResource.class);

    private static final String ENTITY_NAME = "mdmManagerOperationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationTypeService operationTypeService;

    public OperationTypeResource(OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    /**
     * {@code POST  /operation-types} : Create a new operationType.
     *
     * @param operationTypeDTO the operationTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationTypeDTO, or with status {@code 400 (Bad Request)} if the operationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operation-types")
    public ResponseEntity<OperationTypeDTO> createOperationType(@Valid @RequestBody OperationTypeDTO operationTypeDTO) throws URISyntaxException {
        log.debug("REST request to save OperationType : {}", operationTypeDTO);
        if (operationTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new operationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationTypeDTO result = operationTypeService.save(operationTypeDTO);
        return ResponseEntity.created(new URI("/api/operation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operation-types} : Updates an existing operationType.
     *
     * @param operationTypeDTO the operationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the operationTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operation-types")
    public ResponseEntity<OperationTypeDTO> updateOperationType(@Valid @RequestBody OperationTypeDTO operationTypeDTO) throws URISyntaxException {
        log.debug("REST request to update OperationType : {}", operationTypeDTO);
        if (operationTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperationTypeDTO result = operationTypeService.save(operationTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /operation-types} : get all the operationTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationTypes in body.
     */
    @GetMapping("/operation-types")
    public List<OperationTypeDTO> getAllOperationTypes() {
        log.debug("REST request to get all OperationTypes");
        return operationTypeService.findAll();
    }

    /**
     * {@code GET  /operation-types/:id} : get the "id" operationType.
     *
     * @param id the id of the operationTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operation-types/{id}")
    public ResponseEntity<OperationTypeDTO> getOperationType(@PathVariable Long id) {
        log.debug("REST request to get OperationType : {}", id);
        Optional<OperationTypeDTO> operationTypeDTO = operationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationTypeDTO);
    }

    /**
     * {@code DELETE  /operation-types/:id} : delete the "id" operationType.
     *
     * @param id the id of the operationTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operation-types/{id}")
    public ResponseEntity<Void> deleteOperationType(@PathVariable Long id) {
        log.debug("REST request to delete OperationType : {}", id);
        operationTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
