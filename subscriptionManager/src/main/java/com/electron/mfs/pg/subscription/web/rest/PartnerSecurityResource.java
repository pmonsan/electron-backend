package com.electron.mfs.pg.subscription.web.rest;

import com.electron.mfs.pg.subscription.service.PartnerSecurityService;
import com.electron.mfs.pg.subscription.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.subscription.service.dto.PartnerSecurityDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.subscription.domain.PartnerSecurity}.
 */
@RestController
@RequestMapping("/api")
public class PartnerSecurityResource {

    private final Logger log = LoggerFactory.getLogger(PartnerSecurityResource.class);

    private static final String ENTITY_NAME = "subscriptionManagerPartnerSecurity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartnerSecurityService partnerSecurityService;

    public PartnerSecurityResource(PartnerSecurityService partnerSecurityService) {
        this.partnerSecurityService = partnerSecurityService;
    }

    /**
     * {@code POST  /partner-securities} : Create a new partnerSecurity.
     *
     * @param partnerSecurityDTO the partnerSecurityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partnerSecurityDTO, or with status {@code 400 (Bad Request)} if the partnerSecurity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partner-securities")
    public ResponseEntity<PartnerSecurityDTO> createPartnerSecurity(@Valid @RequestBody PartnerSecurityDTO partnerSecurityDTO) throws URISyntaxException {
        log.debug("REST request to save PartnerSecurity : {}", partnerSecurityDTO);
        if (partnerSecurityDTO.getId() != null) {
            throw new BadRequestAlertException("A new partnerSecurity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerSecurityDTO result = partnerSecurityService.save(partnerSecurityDTO);
        return ResponseEntity.created(new URI("/api/partner-securities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partner-securities} : Updates an existing partnerSecurity.
     *
     * @param partnerSecurityDTO the partnerSecurityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerSecurityDTO,
     * or with status {@code 400 (Bad Request)} if the partnerSecurityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partnerSecurityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partner-securities")
    public ResponseEntity<PartnerSecurityDTO> updatePartnerSecurity(@Valid @RequestBody PartnerSecurityDTO partnerSecurityDTO) throws URISyntaxException {
        log.debug("REST request to update PartnerSecurity : {}", partnerSecurityDTO);
        if (partnerSecurityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnerSecurityDTO result = partnerSecurityService.save(partnerSecurityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerSecurityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /partner-securities} : get all the partnerSecurities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partnerSecurities in body.
     */
    @GetMapping("/partner-securities")
    public List<PartnerSecurityDTO> getAllPartnerSecurities() {
        log.debug("REST request to get all PartnerSecurities");
        return partnerSecurityService.findAll();
    }

    /**
     * {@code GET  /partner-securities/:id} : get the "id" partnerSecurity.
     *
     * @param id the id of the partnerSecurityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partnerSecurityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partner-securities/{id}")
    public ResponseEntity<PartnerSecurityDTO> getPartnerSecurity(@PathVariable Long id) {
        log.debug("REST request to get PartnerSecurity : {}", id);
        Optional<PartnerSecurityDTO> partnerSecurityDTO = partnerSecurityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerSecurityDTO);
    }

    /**
     * {@code DELETE  /partner-securities/:id} : delete the "id" partnerSecurity.
     *
     * @param id the id of the partnerSecurityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partner-securities/{id}")
    public ResponseEntity<Void> deletePartnerSecurity(@PathVariable Long id) {
        log.debug("REST request to delete PartnerSecurity : {}", id);
        partnerSecurityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
