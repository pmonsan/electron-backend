package com.electron.mfs.pg.services.web.rest;

import com.electron.mfs.pg.services.service.ConnectorService;
import com.electron.mfs.pg.services.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.services.service.dto.ConnectorDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.services.domain.Connector}.
 */
@RestController
@RequestMapping("/api")
public class ConnectorResource {

    private final Logger log = LoggerFactory.getLogger(ConnectorResource.class);

    private static final String ENTITY_NAME = "servicesManagerConnector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectorService connectorService;

    public ConnectorResource(ConnectorService connectorService) {
        this.connectorService = connectorService;
    }

    /**
     * {@code POST  /connectors} : Create a new connector.
     *
     * @param connectorDTO the connectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectorDTO, or with status {@code 400 (Bad Request)} if the connector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connectors")
    public ResponseEntity<ConnectorDTO> createConnector(@Valid @RequestBody ConnectorDTO connectorDTO) throws URISyntaxException {
        log.debug("REST request to save Connector : {}", connectorDTO);
        if (connectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new connector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectorDTO result = connectorService.save(connectorDTO);
        return ResponseEntity.created(new URI("/api/connectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connectors} : Updates an existing connector.
     *
     * @param connectorDTO the connectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectorDTO,
     * or with status {@code 400 (Bad Request)} if the connectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connectors")
    public ResponseEntity<ConnectorDTO> updateConnector(@Valid @RequestBody ConnectorDTO connectorDTO) throws URISyntaxException {
        log.debug("REST request to update Connector : {}", connectorDTO);
        if (connectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnectorDTO result = connectorService.save(connectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connectors} : get all the connectors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectors in body.
     */
    @GetMapping("/connectors")
    public List<ConnectorDTO> getAllConnectors() {
        log.debug("REST request to get all Connectors");
        return connectorService.findAll();
    }

    /**
     * {@code GET  /connectors/:id} : get the "id" connector.
     *
     * @param id the id of the connectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connectors/{id}")
    public ResponseEntity<ConnectorDTO> getConnector(@PathVariable Long id) {
        log.debug("REST request to get Connector : {}", id);
        Optional<ConnectorDTO> connectorDTO = connectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectorDTO);
    }

    /**
     * {@code DELETE  /connectors/:id} : delete the "id" connector.
     *
     * @param id the id of the connectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connectors/{id}")
    public ResponseEntity<Void> deleteConnector(@PathVariable Long id) {
        log.debug("REST request to delete Connector : {}", id);
        connectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
