package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PricePlanService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PricePlanDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PricePlan}.
 */
@RestController
@RequestMapping("/api")
public class PricePlanResource {

    private final Logger log = LoggerFactory.getLogger(PricePlanResource.class);

    private static final String ENTITY_NAME = "pricePlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PricePlanService pricePlanService;

    public PricePlanResource(PricePlanService pricePlanService) {
        this.pricePlanService = pricePlanService;
    }

    /**
     * {@code POST  /price-plans} : Create a new pricePlan.
     *
     * @param pricePlanDTO the pricePlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pricePlanDTO, or with status {@code 400 (Bad Request)} if the pricePlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/price-plans")
    public ResponseEntity<PricePlanDTO> createPricePlan(@Valid @RequestBody PricePlanDTO pricePlanDTO) throws URISyntaxException {
        log.debug("REST request to save PricePlan : {}", pricePlanDTO);
        if (pricePlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new pricePlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PricePlanDTO result = pricePlanService.save(pricePlanDTO);
        return ResponseEntity.created(new URI("/api/price-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /price-plans} : Updates an existing pricePlan.
     *
     * @param pricePlanDTO the pricePlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pricePlanDTO,
     * or with status {@code 400 (Bad Request)} if the pricePlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pricePlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/price-plans")
    public ResponseEntity<PricePlanDTO> updatePricePlan(@Valid @RequestBody PricePlanDTO pricePlanDTO) throws URISyntaxException {
        log.debug("REST request to update PricePlan : {}", pricePlanDTO);
        if (pricePlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PricePlanDTO result = pricePlanService.save(pricePlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pricePlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /price-plans} : get all the pricePlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pricePlans in body.
     */
    @GetMapping("/price-plans")
    public List<PricePlanDTO> getAllPricePlans() {
        log.debug("REST request to get all PricePlans");
        return pricePlanService.findAll();
    }

    /**
     * {@code GET  /price-plans/:id} : get the "id" pricePlan.
     *
     * @param id the id of the pricePlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pricePlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/price-plans/{id}")
    public ResponseEntity<PricePlanDTO> getPricePlan(@PathVariable Long id) {
        log.debug("REST request to get PricePlan : {}", id);
        Optional<PricePlanDTO> pricePlanDTO = pricePlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pricePlanDTO);
    }

    /**
     * {@code DELETE  /price-plans/:id} : delete the "id" pricePlan.
     *
     * @param id the id of the pricePlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/price-plans/{id}")
    public ResponseEntity<Void> deletePricePlan(@PathVariable Long id) {
        log.debug("REST request to delete PricePlan : {}", id);
        pricePlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
