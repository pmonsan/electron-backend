package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.CustomerSubscriptionService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.CustomerSubscriptionDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.CustomerSubscription}.
 */
@RestController
@RequestMapping("/api")
public class CustomerSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(CustomerSubscriptionResource.class);

    private static final String ENTITY_NAME = "customerSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerSubscriptionService customerSubscriptionService;

    public CustomerSubscriptionResource(CustomerSubscriptionService customerSubscriptionService) {
        this.customerSubscriptionService = customerSubscriptionService;
    }

    /**
     * {@code POST  /customer-subscriptions} : Create a new customerSubscription.
     *
     * @param customerSubscriptionDTO the customerSubscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerSubscriptionDTO, or with status {@code 400 (Bad Request)} if the customerSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-subscriptions")
    public ResponseEntity<CustomerSubscriptionDTO> createCustomerSubscription(@Valid @RequestBody CustomerSubscriptionDTO customerSubscriptionDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerSubscription : {}", customerSubscriptionDTO);
        if (customerSubscriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerSubscriptionDTO result = customerSubscriptionService.save(customerSubscriptionDTO);
        return ResponseEntity.created(new URI("/api/customer-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-subscriptions} : Updates an existing customerSubscription.
     *
     * @param customerSubscriptionDTO the customerSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the customerSubscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-subscriptions")
    public ResponseEntity<CustomerSubscriptionDTO> updateCustomerSubscription(@Valid @RequestBody CustomerSubscriptionDTO customerSubscriptionDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerSubscription : {}", customerSubscriptionDTO);
        if (customerSubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerSubscriptionDTO result = customerSubscriptionService.save(customerSubscriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerSubscriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-subscriptions} : get all the customerSubscriptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerSubscriptions in body.
     */
    @GetMapping("/customer-subscriptions")
    public List<CustomerSubscriptionDTO> getAllCustomerSubscriptions() {
        log.debug("REST request to get all CustomerSubscriptions");
        return customerSubscriptionService.findAll();
    }

    /**
     * {@code GET  /customer-subscriptions/:id} : get the "id" customerSubscription.
     *
     * @param id the id of the customerSubscriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerSubscriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-subscriptions/{id}")
    public ResponseEntity<CustomerSubscriptionDTO> getCustomerSubscription(@PathVariable Long id) {
        log.debug("REST request to get CustomerSubscription : {}", id);
        Optional<CustomerSubscriptionDTO> customerSubscriptionDTO = customerSubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerSubscriptionDTO);
    }

    /**
     * {@code DELETE  /customer-subscriptions/:id} : delete the "id" customerSubscription.
     *
     * @param id the id of the customerSubscriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-subscriptions/{id}")
    public ResponseEntity<Void> deleteCustomerSubscription(@PathVariable Long id) {
        log.debug("REST request to delete CustomerSubscription : {}", id);
        customerSubscriptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
