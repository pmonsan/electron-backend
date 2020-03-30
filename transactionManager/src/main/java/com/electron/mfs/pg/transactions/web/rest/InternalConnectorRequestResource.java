package com.electron.mfs.pg.transactions.web.rest;

import com.electron.mfs.pg.transactions.service.InternalConnectorRequestService;
import com.electron.mfs.pg.transactions.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.transactions.service.dto.InternalConnectorRequestDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.transactions.domain.InternalConnectorRequest}.
 */
@RestController
@RequestMapping("/api")
public class InternalConnectorRequestResource {

    private final Logger log = LoggerFactory.getLogger(InternalConnectorRequestResource.class);

    private static final String ENTITY_NAME = "transactionManagerInternalConnectorRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InternalConnectorRequestService internalConnectorRequestService;

    public InternalConnectorRequestResource(InternalConnectorRequestService internalConnectorRequestService) {
        this.internalConnectorRequestService = internalConnectorRequestService;
    }

    /**
     * {@code POST  /internal-connector-requests} : Create a new internalConnectorRequest.
     *
     * @param internalConnectorRequestDTO the internalConnectorRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new internalConnectorRequestDTO, or with status {@code 400 (Bad Request)} if the internalConnectorRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/internal-connector-requests")
    public ResponseEntity<InternalConnectorRequestDTO> createInternalConnectorRequest(@Valid @RequestBody InternalConnectorRequestDTO internalConnectorRequestDTO) throws URISyntaxException {
        log.debug("REST request to save InternalConnectorRequest : {}", internalConnectorRequestDTO);
        if (internalConnectorRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new internalConnectorRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternalConnectorRequestDTO result = internalConnectorRequestService.save(internalConnectorRequestDTO);
        return ResponseEntity.created(new URI("/api/internal-connector-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /internal-connector-requests} : Updates an existing internalConnectorRequest.
     *
     * @param internalConnectorRequestDTO the internalConnectorRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internalConnectorRequestDTO,
     * or with status {@code 400 (Bad Request)} if the internalConnectorRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the internalConnectorRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/internal-connector-requests")
    public ResponseEntity<InternalConnectorRequestDTO> updateInternalConnectorRequest(@Valid @RequestBody InternalConnectorRequestDTO internalConnectorRequestDTO) throws URISyntaxException {
        log.debug("REST request to update InternalConnectorRequest : {}", internalConnectorRequestDTO);
        if (internalConnectorRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InternalConnectorRequestDTO result = internalConnectorRequestService.save(internalConnectorRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internalConnectorRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /internal-connector-requests} : get all the internalConnectorRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of internalConnectorRequests in body.
     */
    @GetMapping("/internal-connector-requests")
    public List<InternalConnectorRequestDTO> getAllInternalConnectorRequests() {
        log.debug("REST request to get all InternalConnectorRequests");
        return internalConnectorRequestService.findAll();
    }

    /**
     * {@code GET  /internal-connector-requests/:id} : get the "id" internalConnectorRequest.
     *
     * @param id the id of the internalConnectorRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the internalConnectorRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/internal-connector-requests/{id}")
    public ResponseEntity<InternalConnectorRequestDTO> getInternalConnectorRequest(@PathVariable Long id) {
        log.debug("REST request to get InternalConnectorRequest : {}", id);
        Optional<InternalConnectorRequestDTO> internalConnectorRequestDTO = internalConnectorRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internalConnectorRequestDTO);
    }

    /**
     * {@code DELETE  /internal-connector-requests/:id} : delete the "id" internalConnectorRequest.
     *
     * @param id the id of the internalConnectorRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/internal-connector-requests/{id}")
    public ResponseEntity<Void> deleteInternalConnectorRequest(@PathVariable Long id) {
        log.debug("REST request to delete InternalConnectorRequest : {}", id);
        internalConnectorRequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
