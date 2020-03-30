package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.ConnectorTypeService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.ConnectorTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.ConnectorType}.
 */
@RestController
@RequestMapping("/api")
public class ConnectorTypeResource {

    private final Logger log = LoggerFactory.getLogger(ConnectorTypeResource.class);

    private static final String ENTITY_NAME = "connectorType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectorTypeService connectorTypeService;

    public ConnectorTypeResource(ConnectorTypeService connectorTypeService) {
        this.connectorTypeService = connectorTypeService;
    }

    /**
     * {@code POST  /connector-types} : Create a new connectorType.
     *
     * @param connectorTypeDTO the connectorTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectorTypeDTO, or with status {@code 400 (Bad Request)} if the connectorType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connector-types")
    public ResponseEntity<ConnectorTypeDTO> createConnectorType(@Valid @RequestBody ConnectorTypeDTO connectorTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ConnectorType : {}", connectorTypeDTO);
        if (connectorTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new connectorType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectorTypeDTO result = connectorTypeService.save(connectorTypeDTO);
        return ResponseEntity.created(new URI("/api/connector-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connector-types} : Updates an existing connectorType.
     *
     * @param connectorTypeDTO the connectorTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectorTypeDTO,
     * or with status {@code 400 (Bad Request)} if the connectorTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectorTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connector-types")
    public ResponseEntity<ConnectorTypeDTO> updateConnectorType(@Valid @RequestBody ConnectorTypeDTO connectorTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ConnectorType : {}", connectorTypeDTO);
        if (connectorTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnectorTypeDTO result = connectorTypeService.save(connectorTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectorTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connector-types} : get all the connectorTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectorTypes in body.
     */
    @GetMapping("/connector-types")
    public List<ConnectorTypeDTO> getAllConnectorTypes() {
        log.debug("REST request to get all ConnectorTypes");
        return connectorTypeService.findAll();
    }

    /**
     * {@code GET  /connector-types/:id} : get the "id" connectorType.
     *
     * @param id the id of the connectorTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectorTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connector-types/{id}")
    public ResponseEntity<ConnectorTypeDTO> getConnectorType(@PathVariable Long id) {
        log.debug("REST request to get ConnectorType : {}", id);
        Optional<ConnectorTypeDTO> connectorTypeDTO = connectorTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectorTypeDTO);
    }

    /**
     * {@code DELETE  /connector-types/:id} : delete the "id" connectorType.
     *
     * @param id the id of the connectorTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connector-types/{id}")
    public ResponseEntity<Void> deleteConnectorType(@PathVariable Long id) {
        log.debug("REST request to delete ConnectorType : {}", id);
        connectorTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
