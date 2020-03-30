package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.ServiceIntegrationService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.ServiceIntegrationDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.ServiceIntegration}.
 */
@RestController
@RequestMapping("/api")
public class ServiceIntegrationResource {

    private final Logger log = LoggerFactory.getLogger(ServiceIntegrationResource.class);

    private static final String ENTITY_NAME = "serviceIntegration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceIntegrationService serviceIntegrationService;

    public ServiceIntegrationResource(ServiceIntegrationService serviceIntegrationService) {
        this.serviceIntegrationService = serviceIntegrationService;
    }

    /**
     * {@code POST  /service-integrations} : Create a new serviceIntegration.
     *
     * @param serviceIntegrationDTO the serviceIntegrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceIntegrationDTO, or with status {@code 400 (Bad Request)} if the serviceIntegration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-integrations")
    public ResponseEntity<ServiceIntegrationDTO> createServiceIntegration(@Valid @RequestBody ServiceIntegrationDTO serviceIntegrationDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceIntegration : {}", serviceIntegrationDTO);
        if (serviceIntegrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceIntegration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceIntegrationDTO result = serviceIntegrationService.save(serviceIntegrationDTO);
        return ResponseEntity.created(new URI("/api/service-integrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-integrations} : Updates an existing serviceIntegration.
     *
     * @param serviceIntegrationDTO the serviceIntegrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceIntegrationDTO,
     * or with status {@code 400 (Bad Request)} if the serviceIntegrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceIntegrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-integrations")
    public ResponseEntity<ServiceIntegrationDTO> updateServiceIntegration(@Valid @RequestBody ServiceIntegrationDTO serviceIntegrationDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceIntegration : {}", serviceIntegrationDTO);
        if (serviceIntegrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceIntegrationDTO result = serviceIntegrationService.save(serviceIntegrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceIntegrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-integrations} : get all the serviceIntegrations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceIntegrations in body.
     */
    @GetMapping("/service-integrations")
    public List<ServiceIntegrationDTO> getAllServiceIntegrations() {
        log.debug("REST request to get all ServiceIntegrations");
        return serviceIntegrationService.findAll();
    }

    /**
     * {@code GET  /service-integrations/:id} : get the "id" serviceIntegration.
     *
     * @param id the id of the serviceIntegrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceIntegrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-integrations/{id}")
    public ResponseEntity<ServiceIntegrationDTO> getServiceIntegration(@PathVariable Long id) {
        log.debug("REST request to get ServiceIntegration : {}", id);
        Optional<ServiceIntegrationDTO> serviceIntegrationDTO = serviceIntegrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceIntegrationDTO);
    }

    /**
     * {@code DELETE  /service-integrations/:id} : delete the "id" serviceIntegration.
     *
     * @param id the id of the serviceIntegrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-integrations/{id}")
    public ResponseEntity<Void> deleteServiceIntegration(@PathVariable Long id) {
        log.debug("REST request to delete ServiceIntegration : {}", id);
        serviceIntegrationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
