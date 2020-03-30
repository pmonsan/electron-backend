package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.ServiceLimitService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.ServiceLimitDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.ServiceLimit}.
 */
@RestController
@RequestMapping("/api")
public class ServiceLimitResource {

    private final Logger log = LoggerFactory.getLogger(ServiceLimitResource.class);

    private static final String ENTITY_NAME = "serviceLimit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceLimitService serviceLimitService;

    public ServiceLimitResource(ServiceLimitService serviceLimitService) {
        this.serviceLimitService = serviceLimitService;
    }

    /**
     * {@code POST  /service-limits} : Create a new serviceLimit.
     *
     * @param serviceLimitDTO the serviceLimitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceLimitDTO, or with status {@code 400 (Bad Request)} if the serviceLimit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-limits")
    public ResponseEntity<ServiceLimitDTO> createServiceLimit(@Valid @RequestBody ServiceLimitDTO serviceLimitDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceLimit : {}", serviceLimitDTO);
        if (serviceLimitDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceLimit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceLimitDTO result = serviceLimitService.save(serviceLimitDTO);
        return ResponseEntity.created(new URI("/api/service-limits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-limits} : Updates an existing serviceLimit.
     *
     * @param serviceLimitDTO the serviceLimitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceLimitDTO,
     * or with status {@code 400 (Bad Request)} if the serviceLimitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceLimitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-limits")
    public ResponseEntity<ServiceLimitDTO> updateServiceLimit(@Valid @RequestBody ServiceLimitDTO serviceLimitDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceLimit : {}", serviceLimitDTO);
        if (serviceLimitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceLimitDTO result = serviceLimitService.save(serviceLimitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceLimitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-limits} : get all the serviceLimits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceLimits in body.
     */
    @GetMapping("/service-limits")
    public List<ServiceLimitDTO> getAllServiceLimits() {
        log.debug("REST request to get all ServiceLimits");
        return serviceLimitService.findAll();
    }

    /**
     * {@code GET  /service-limits/:id} : get the "id" serviceLimit.
     *
     * @param id the id of the serviceLimitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceLimitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-limits/{id}")
    public ResponseEntity<ServiceLimitDTO> getServiceLimit(@PathVariable Long id) {
        log.debug("REST request to get ServiceLimit : {}", id);
        Optional<ServiceLimitDTO> serviceLimitDTO = serviceLimitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceLimitDTO);
    }

    /**
     * {@code DELETE  /service-limits/:id} : delete the "id" serviceLimit.
     *
     * @param id the id of the serviceLimitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-limits/{id}")
    public ResponseEntity<Void> deleteServiceLimit(@PathVariable Long id) {
        log.debug("REST request to delete ServiceLimit : {}", id);
        serviceLimitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
