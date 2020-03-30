package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.ServiceChannelService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.ServiceChannelDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.ServiceChannel}.
 */
@RestController
@RequestMapping("/api")
public class ServiceChannelResource {

    private final Logger log = LoggerFactory.getLogger(ServiceChannelResource.class);

    private static final String ENTITY_NAME = "serviceChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceChannelService serviceChannelService;

    public ServiceChannelResource(ServiceChannelService serviceChannelService) {
        this.serviceChannelService = serviceChannelService;
    }

    /**
     * {@code POST  /service-channels} : Create a new serviceChannel.
     *
     * @param serviceChannelDTO the serviceChannelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceChannelDTO, or with status {@code 400 (Bad Request)} if the serviceChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-channels")
    public ResponseEntity<ServiceChannelDTO> createServiceChannel(@Valid @RequestBody ServiceChannelDTO serviceChannelDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceChannel : {}", serviceChannelDTO);
        if (serviceChannelDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceChannelDTO result = serviceChannelService.save(serviceChannelDTO);
        return ResponseEntity.created(new URI("/api/service-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-channels} : Updates an existing serviceChannel.
     *
     * @param serviceChannelDTO the serviceChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceChannelDTO,
     * or with status {@code 400 (Bad Request)} if the serviceChannelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-channels")
    public ResponseEntity<ServiceChannelDTO> updateServiceChannel(@Valid @RequestBody ServiceChannelDTO serviceChannelDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceChannel : {}", serviceChannelDTO);
        if (serviceChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceChannelDTO result = serviceChannelService.save(serviceChannelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-channels} : get all the serviceChannels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceChannels in body.
     */
    @GetMapping("/service-channels")
    public List<ServiceChannelDTO> getAllServiceChannels() {
        log.debug("REST request to get all ServiceChannels");
        return serviceChannelService.findAll();
    }

    /**
     * {@code GET  /service-channels/:id} : get the "id" serviceChannel.
     *
     * @param id the id of the serviceChannelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceChannelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-channels/{id}")
    public ResponseEntity<ServiceChannelDTO> getServiceChannel(@PathVariable Long id) {
        log.debug("REST request to get ServiceChannel : {}", id);
        Optional<ServiceChannelDTO> serviceChannelDTO = serviceChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceChannelDTO);
    }

    /**
     * {@code DELETE  /service-channels/:id} : delete the "id" serviceChannel.
     *
     * @param id the id of the serviceChannelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-channels/{id}")
    public ResponseEntity<Void> deleteServiceChannel(@PathVariable Long id) {
        log.debug("REST request to delete ServiceChannel : {}", id);
        serviceChannelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
