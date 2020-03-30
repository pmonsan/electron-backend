package com.electron.mfs.pg.feescommission.web.rest;

import com.electron.mfs.pg.feescommission.service.PriceCommissionService;
import com.electron.mfs.pg.feescommission.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.feescommission.service.dto.PriceCommissionDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.feescommission.domain.PriceCommission}.
 */
@RestController
@RequestMapping("/api")
public class PriceCommissionResource {

    private final Logger log = LoggerFactory.getLogger(PriceCommissionResource.class);

    private static final String ENTITY_NAME = "feesCommissionManagerPriceCommission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriceCommissionService priceCommissionService;

    public PriceCommissionResource(PriceCommissionService priceCommissionService) {
        this.priceCommissionService = priceCommissionService;
    }

    /**
     * {@code POST  /price-commissions} : Create a new priceCommission.
     *
     * @param priceCommissionDTO the priceCommissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priceCommissionDTO, or with status {@code 400 (Bad Request)} if the priceCommission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/price-commissions")
    public ResponseEntity<PriceCommissionDTO> createPriceCommission(@Valid @RequestBody PriceCommissionDTO priceCommissionDTO) throws URISyntaxException {
        log.debug("REST request to save PriceCommission : {}", priceCommissionDTO);
        if (priceCommissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new priceCommission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriceCommissionDTO result = priceCommissionService.save(priceCommissionDTO);
        return ResponseEntity.created(new URI("/api/price-commissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /price-commissions} : Updates an existing priceCommission.
     *
     * @param priceCommissionDTO the priceCommissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priceCommissionDTO,
     * or with status {@code 400 (Bad Request)} if the priceCommissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priceCommissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/price-commissions")
    public ResponseEntity<PriceCommissionDTO> updatePriceCommission(@Valid @RequestBody PriceCommissionDTO priceCommissionDTO) throws URISyntaxException {
        log.debug("REST request to update PriceCommission : {}", priceCommissionDTO);
        if (priceCommissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriceCommissionDTO result = priceCommissionService.save(priceCommissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, priceCommissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /price-commissions} : get all the priceCommissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priceCommissions in body.
     */
    @GetMapping("/price-commissions")
    public List<PriceCommissionDTO> getAllPriceCommissions() {
        log.debug("REST request to get all PriceCommissions");
        return priceCommissionService.findAll();
    }

    /**
     * {@code GET  /price-commissions/:id} : get the "id" priceCommission.
     *
     * @param id the id of the priceCommissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priceCommissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/price-commissions/{id}")
    public ResponseEntity<PriceCommissionDTO> getPriceCommission(@PathVariable Long id) {
        log.debug("REST request to get PriceCommission : {}", id);
        Optional<PriceCommissionDTO> priceCommissionDTO = priceCommissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceCommissionDTO);
    }

    /**
     * {@code DELETE  /price-commissions/:id} : delete the "id" priceCommission.
     *
     * @param id the id of the priceCommissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/price-commissions/{id}")
    public ResponseEntity<Void> deletePriceCommission(@PathVariable Long id) {
        log.debug("REST request to delete PriceCommission : {}", id);
        priceCommissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
