package com.electron.mfs.pg.feescommission.web.rest;

import com.electron.mfs.pg.feescommission.service.SubscriptionPriceService;
import com.electron.mfs.pg.feescommission.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.feescommission.service.dto.SubscriptionPriceDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.feescommission.domain.SubscriptionPrice}.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionPriceResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionPriceResource.class);

    private static final String ENTITY_NAME = "feesCommissionManagerSubscriptionPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscriptionPriceService subscriptionPriceService;

    public SubscriptionPriceResource(SubscriptionPriceService subscriptionPriceService) {
        this.subscriptionPriceService = subscriptionPriceService;
    }

    /**
     * {@code POST  /subscription-prices} : Create a new subscriptionPrice.
     *
     * @param subscriptionPriceDTO the subscriptionPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscriptionPriceDTO, or with status {@code 400 (Bad Request)} if the subscriptionPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subscription-prices")
    public ResponseEntity<SubscriptionPriceDTO> createSubscriptionPrice(@Valid @RequestBody SubscriptionPriceDTO subscriptionPriceDTO) throws URISyntaxException {
        log.debug("REST request to save SubscriptionPrice : {}", subscriptionPriceDTO);
        if (subscriptionPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new subscriptionPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubscriptionPriceDTO result = subscriptionPriceService.save(subscriptionPriceDTO);
        return ResponseEntity.created(new URI("/api/subscription-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subscription-prices} : Updates an existing subscriptionPrice.
     *
     * @param subscriptionPriceDTO the subscriptionPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionPriceDTO,
     * or with status {@code 400 (Bad Request)} if the subscriptionPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subscription-prices")
    public ResponseEntity<SubscriptionPriceDTO> updateSubscriptionPrice(@Valid @RequestBody SubscriptionPriceDTO subscriptionPriceDTO) throws URISyntaxException {
        log.debug("REST request to update SubscriptionPrice : {}", subscriptionPriceDTO);
        if (subscriptionPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubscriptionPriceDTO result = subscriptionPriceService.save(subscriptionPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriptionPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subscription-prices} : get all the subscriptionPrices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscriptionPrices in body.
     */
    @GetMapping("/subscription-prices")
    public List<SubscriptionPriceDTO> getAllSubscriptionPrices() {
        log.debug("REST request to get all SubscriptionPrices");
        return subscriptionPriceService.findAll();
    }

    /**
     * {@code GET  /subscription-prices/:id} : get the "id" subscriptionPrice.
     *
     * @param id the id of the subscriptionPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscriptionPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subscription-prices/{id}")
    public ResponseEntity<SubscriptionPriceDTO> getSubscriptionPrice(@PathVariable Long id) {
        log.debug("REST request to get SubscriptionPrice : {}", id);
        Optional<SubscriptionPriceDTO> subscriptionPriceDTO = subscriptionPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subscriptionPriceDTO);
    }

    /**
     * {@code DELETE  /subscription-prices/:id} : delete the "id" subscriptionPrice.
     *
     * @param id the id of the subscriptionPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subscription-prices/{id}")
    public ResponseEntity<Void> deleteSubscriptionPrice(@PathVariable Long id) {
        log.debug("REST request to delete SubscriptionPrice : {}", id);
        subscriptionPriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
