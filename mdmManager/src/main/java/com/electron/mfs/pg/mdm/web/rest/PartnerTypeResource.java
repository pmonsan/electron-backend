package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.PartnerTypeService;
import com.electron.mfs.pg.mdm.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.mdm.service.dto.PartnerTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.mdm.domain.PartnerType}.
 */
@RestController
@RequestMapping("/api")
public class PartnerTypeResource {

    private final Logger log = LoggerFactory.getLogger(PartnerTypeResource.class);

    private static final String ENTITY_NAME = "mdmManagerPartnerType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartnerTypeService partnerTypeService;

    public PartnerTypeResource(PartnerTypeService partnerTypeService) {
        this.partnerTypeService = partnerTypeService;
    }

    /**
     * {@code POST  /partner-types} : Create a new partnerType.
     *
     * @param partnerTypeDTO the partnerTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partnerTypeDTO, or with status {@code 400 (Bad Request)} if the partnerType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partner-types")
    public ResponseEntity<PartnerTypeDTO> createPartnerType(@Valid @RequestBody PartnerTypeDTO partnerTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PartnerType : {}", partnerTypeDTO);
        if (partnerTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new partnerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerTypeDTO result = partnerTypeService.save(partnerTypeDTO);
        return ResponseEntity.created(new URI("/api/partner-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partner-types} : Updates an existing partnerType.
     *
     * @param partnerTypeDTO the partnerTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerTypeDTO,
     * or with status {@code 400 (Bad Request)} if the partnerTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partnerTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partner-types")
    public ResponseEntity<PartnerTypeDTO> updatePartnerType(@Valid @RequestBody PartnerTypeDTO partnerTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PartnerType : {}", partnerTypeDTO);
        if (partnerTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnerTypeDTO result = partnerTypeService.save(partnerTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /partner-types} : get all the partnerTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partnerTypes in body.
     */
    @GetMapping("/partner-types")
    public List<PartnerTypeDTO> getAllPartnerTypes() {
        log.debug("REST request to get all PartnerTypes");
        return partnerTypeService.findAll();
    }

    /**
     * {@code GET  /partner-types/:id} : get the "id" partnerType.
     *
     * @param id the id of the partnerTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partnerTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partner-types/{id}")
    public ResponseEntity<PartnerTypeDTO> getPartnerType(@PathVariable Long id) {
        log.debug("REST request to get PartnerType : {}", id);
        Optional<PartnerTypeDTO> partnerTypeDTO = partnerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerTypeDTO);
    }

    /**
     * {@code DELETE  /partner-types/:id} : delete the "id" partnerType.
     *
     * @param id the id of the partnerTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partner-types/{id}")
    public ResponseEntity<Void> deletePartnerType(@PathVariable Long id) {
        log.debug("REST request to delete PartnerType : {}", id);
        partnerTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
