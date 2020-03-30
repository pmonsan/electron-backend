package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.ServiceAuthenticationService;
import com.electron.mfs.pg.mdm.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.mdm.service.dto.ServiceAuthenticationDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.mdm.domain.ServiceAuthentication}.
 */
@RestController
@RequestMapping("/api")
public class ServiceAuthenticationResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAuthenticationResource.class);

    private static final String ENTITY_NAME = "mdmManagerServiceAuthentication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceAuthenticationService serviceAuthenticationService;

    public ServiceAuthenticationResource(ServiceAuthenticationService serviceAuthenticationService) {
        this.serviceAuthenticationService = serviceAuthenticationService;
    }

    /**
     * {@code POST  /service-authentications} : Create a new serviceAuthentication.
     *
     * @param serviceAuthenticationDTO the serviceAuthenticationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceAuthenticationDTO, or with status {@code 400 (Bad Request)} if the serviceAuthentication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-authentications")
    public ResponseEntity<ServiceAuthenticationDTO> createServiceAuthentication(@Valid @RequestBody ServiceAuthenticationDTO serviceAuthenticationDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceAuthentication : {}", serviceAuthenticationDTO);
        if (serviceAuthenticationDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceAuthentication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceAuthenticationDTO result = serviceAuthenticationService.save(serviceAuthenticationDTO);
        return ResponseEntity.created(new URI("/api/service-authentications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-authentications} : Updates an existing serviceAuthentication.
     *
     * @param serviceAuthenticationDTO the serviceAuthenticationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceAuthenticationDTO,
     * or with status {@code 400 (Bad Request)} if the serviceAuthenticationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceAuthenticationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-authentications")
    public ResponseEntity<ServiceAuthenticationDTO> updateServiceAuthentication(@Valid @RequestBody ServiceAuthenticationDTO serviceAuthenticationDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceAuthentication : {}", serviceAuthenticationDTO);
        if (serviceAuthenticationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceAuthenticationDTO result = serviceAuthenticationService.save(serviceAuthenticationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceAuthenticationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-authentications} : get all the serviceAuthentications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceAuthentications in body.
     */
    @GetMapping("/service-authentications")
    public List<ServiceAuthenticationDTO> getAllServiceAuthentications() {
        log.debug("REST request to get all ServiceAuthentications");
        return serviceAuthenticationService.findAll();
    }

    /**
     * {@code GET  /service-authentications/:id} : get the "id" serviceAuthentication.
     *
     * @param id the id of the serviceAuthenticationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceAuthenticationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-authentications/{id}")
    public ResponseEntity<ServiceAuthenticationDTO> getServiceAuthentication(@PathVariable Long id) {
        log.debug("REST request to get ServiceAuthentication : {}", id);
        Optional<ServiceAuthenticationDTO> serviceAuthenticationDTO = serviceAuthenticationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceAuthenticationDTO);
    }

    /**
     * {@code DELETE  /service-authentications/:id} : delete the "id" serviceAuthentication.
     *
     * @param id the id of the serviceAuthenticationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-authentications/{id}")
    public ResponseEntity<Void> deleteServiceAuthentication(@PathVariable Long id) {
        log.debug("REST request to delete ServiceAuthentication : {}", id);
        serviceAuthenticationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
