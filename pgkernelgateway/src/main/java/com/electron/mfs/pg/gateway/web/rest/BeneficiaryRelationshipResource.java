package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.BeneficiaryRelationshipService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.BeneficiaryRelationshipDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.BeneficiaryRelationship}.
 */
@RestController
@RequestMapping("/api")
public class BeneficiaryRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryRelationshipResource.class);

    private static final String ENTITY_NAME = "beneficiaryRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeneficiaryRelationshipService beneficiaryRelationshipService;

    public BeneficiaryRelationshipResource(BeneficiaryRelationshipService beneficiaryRelationshipService) {
        this.beneficiaryRelationshipService = beneficiaryRelationshipService;
    }

    /**
     * {@code POST  /beneficiary-relationships} : Create a new beneficiaryRelationship.
     *
     * @param beneficiaryRelationshipDTO the beneficiaryRelationshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beneficiaryRelationshipDTO, or with status {@code 400 (Bad Request)} if the beneficiaryRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beneficiary-relationships")
    public ResponseEntity<BeneficiaryRelationshipDTO> createBeneficiaryRelationship(@Valid @RequestBody BeneficiaryRelationshipDTO beneficiaryRelationshipDTO) throws URISyntaxException {
        log.debug("REST request to save BeneficiaryRelationship : {}", beneficiaryRelationshipDTO);
        if (beneficiaryRelationshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new beneficiaryRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeneficiaryRelationshipDTO result = beneficiaryRelationshipService.save(beneficiaryRelationshipDTO);
        return ResponseEntity.created(new URI("/api/beneficiary-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beneficiary-relationships} : Updates an existing beneficiaryRelationship.
     *
     * @param beneficiaryRelationshipDTO the beneficiaryRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beneficiaryRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the beneficiaryRelationshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beneficiaryRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beneficiary-relationships")
    public ResponseEntity<BeneficiaryRelationshipDTO> updateBeneficiaryRelationship(@Valid @RequestBody BeneficiaryRelationshipDTO beneficiaryRelationshipDTO) throws URISyntaxException {
        log.debug("REST request to update BeneficiaryRelationship : {}", beneficiaryRelationshipDTO);
        if (beneficiaryRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BeneficiaryRelationshipDTO result = beneficiaryRelationshipService.save(beneficiaryRelationshipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryRelationshipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /beneficiary-relationships} : get all the beneficiaryRelationships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beneficiaryRelationships in body.
     */
    @GetMapping("/beneficiary-relationships")
    public List<BeneficiaryRelationshipDTO> getAllBeneficiaryRelationships() {
        log.debug("REST request to get all BeneficiaryRelationships");
        return beneficiaryRelationshipService.findAll();
    }

    /**
     * {@code GET  /beneficiary-relationships/:id} : get the "id" beneficiaryRelationship.
     *
     * @param id the id of the beneficiaryRelationshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiaryRelationshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiary-relationships/{id}")
    public ResponseEntity<BeneficiaryRelationshipDTO> getBeneficiaryRelationship(@PathVariable Long id) {
        log.debug("REST request to get BeneficiaryRelationship : {}", id);
        Optional<BeneficiaryRelationshipDTO> beneficiaryRelationshipDTO = beneficiaryRelationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beneficiaryRelationshipDTO);
    }

    /**
     * {@code DELETE  /beneficiary-relationships/:id} : delete the "id" beneficiaryRelationship.
     *
     * @param id the id of the beneficiaryRelationshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beneficiary-relationships/{id}")
    public ResponseEntity<Void> deleteBeneficiaryRelationship(@PathVariable Long id) {
        log.debug("REST request to delete BeneficiaryRelationship : {}", id);
        beneficiaryRelationshipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
