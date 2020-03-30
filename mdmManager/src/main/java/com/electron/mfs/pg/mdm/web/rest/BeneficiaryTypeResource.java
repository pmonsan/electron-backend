package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.BeneficiaryTypeService;
import com.electron.mfs.pg.mdm.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.mdm.service.dto.BeneficiaryTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.mdm.domain.BeneficiaryType}.
 */
@RestController
@RequestMapping("/api")
public class BeneficiaryTypeResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryTypeResource.class);

    private static final String ENTITY_NAME = "mdmManagerBeneficiaryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeneficiaryTypeService beneficiaryTypeService;

    public BeneficiaryTypeResource(BeneficiaryTypeService beneficiaryTypeService) {
        this.beneficiaryTypeService = beneficiaryTypeService;
    }

    /**
     * {@code POST  /beneficiary-types} : Create a new beneficiaryType.
     *
     * @param beneficiaryTypeDTO the beneficiaryTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beneficiaryTypeDTO, or with status {@code 400 (Bad Request)} if the beneficiaryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beneficiary-types")
    public ResponseEntity<BeneficiaryTypeDTO> createBeneficiaryType(@Valid @RequestBody BeneficiaryTypeDTO beneficiaryTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BeneficiaryType : {}", beneficiaryTypeDTO);
        if (beneficiaryTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new beneficiaryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeneficiaryTypeDTO result = beneficiaryTypeService.save(beneficiaryTypeDTO);
        return ResponseEntity.created(new URI("/api/beneficiary-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beneficiary-types} : Updates an existing beneficiaryType.
     *
     * @param beneficiaryTypeDTO the beneficiaryTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beneficiaryTypeDTO,
     * or with status {@code 400 (Bad Request)} if the beneficiaryTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beneficiaryTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beneficiary-types")
    public ResponseEntity<BeneficiaryTypeDTO> updateBeneficiaryType(@Valid @RequestBody BeneficiaryTypeDTO beneficiaryTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BeneficiaryType : {}", beneficiaryTypeDTO);
        if (beneficiaryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BeneficiaryTypeDTO result = beneficiaryTypeService.save(beneficiaryTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /beneficiary-types} : get all the beneficiaryTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beneficiaryTypes in body.
     */
    @GetMapping("/beneficiary-types")
    public List<BeneficiaryTypeDTO> getAllBeneficiaryTypes() {
        log.debug("REST request to get all BeneficiaryTypes");
        return beneficiaryTypeService.findAll();
    }

    /**
     * {@code GET  /beneficiary-types/:id} : get the "id" beneficiaryType.
     *
     * @param id the id of the beneficiaryTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiaryTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiary-types/{id}")
    public ResponseEntity<BeneficiaryTypeDTO> getBeneficiaryType(@PathVariable Long id) {
        log.debug("REST request to get BeneficiaryType : {}", id);
        Optional<BeneficiaryTypeDTO> beneficiaryTypeDTO = beneficiaryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beneficiaryTypeDTO);
    }

    /**
     * {@code DELETE  /beneficiary-types/:id} : delete the "id" beneficiaryType.
     *
     * @param id the id of the beneficiaryTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beneficiary-types/{id}")
    public ResponseEntity<Void> deleteBeneficiaryType(@PathVariable Long id) {
        log.debug("REST request to delete BeneficiaryType : {}", id);
        beneficiaryTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
