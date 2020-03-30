package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.MeansofpaymentTypeService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.MeansofpaymentType}.
 */
@RestController
@RequestMapping("/api")
public class MeansofpaymentTypeResource {

    private final Logger log = LoggerFactory.getLogger(MeansofpaymentTypeResource.class);

    private static final String ENTITY_NAME = "meansofpaymentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeansofpaymentTypeService meansofpaymentTypeService;

    public MeansofpaymentTypeResource(MeansofpaymentTypeService meansofpaymentTypeService) {
        this.meansofpaymentTypeService = meansofpaymentTypeService;
    }

    /**
     * {@code POST  /meansofpayment-types} : Create a new meansofpaymentType.
     *
     * @param meansofpaymentTypeDTO the meansofpaymentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meansofpaymentTypeDTO, or with status {@code 400 (Bad Request)} if the meansofpaymentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meansofpayment-types")
    public ResponseEntity<MeansofpaymentTypeDTO> createMeansofpaymentType(@Valid @RequestBody MeansofpaymentTypeDTO meansofpaymentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save MeansofpaymentType : {}", meansofpaymentTypeDTO);
        if (meansofpaymentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new meansofpaymentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeansofpaymentTypeDTO result = meansofpaymentTypeService.save(meansofpaymentTypeDTO);
        return ResponseEntity.created(new URI("/api/meansofpayment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meansofpayment-types} : Updates an existing meansofpaymentType.
     *
     * @param meansofpaymentTypeDTO the meansofpaymentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meansofpaymentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the meansofpaymentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meansofpaymentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meansofpayment-types")
    public ResponseEntity<MeansofpaymentTypeDTO> updateMeansofpaymentType(@Valid @RequestBody MeansofpaymentTypeDTO meansofpaymentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update MeansofpaymentType : {}", meansofpaymentTypeDTO);
        if (meansofpaymentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeansofpaymentTypeDTO result = meansofpaymentTypeService.save(meansofpaymentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meansofpaymentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meansofpayment-types} : get all the meansofpaymentTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meansofpaymentTypes in body.
     */
    @GetMapping("/meansofpayment-types")
    public List<MeansofpaymentTypeDTO> getAllMeansofpaymentTypes() {
        log.debug("REST request to get all MeansofpaymentTypes");
        return meansofpaymentTypeService.findAll();
    }

    /**
     * {@code GET  /meansofpayment-types/:id} : get the "id" meansofpaymentType.
     *
     * @param id the id of the meansofpaymentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meansofpaymentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meansofpayment-types/{id}")
    public ResponseEntity<MeansofpaymentTypeDTO> getMeansofpaymentType(@PathVariable Long id) {
        log.debug("REST request to get MeansofpaymentType : {}", id);
        Optional<MeansofpaymentTypeDTO> meansofpaymentTypeDTO = meansofpaymentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meansofpaymentTypeDTO);
    }

    /**
     * {@code DELETE  /meansofpayment-types/:id} : delete the "id" meansofpaymentType.
     *
     * @param id the id of the meansofpaymentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meansofpayment-types/{id}")
    public ResponseEntity<Void> deleteMeansofpaymentType(@PathVariable Long id) {
        log.debug("REST request to delete MeansofpaymentType : {}", id);
        meansofpaymentTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
