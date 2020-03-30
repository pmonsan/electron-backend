package com.electron.mfs.pg.customer.web.rest;

import com.electron.mfs.pg.customer.service.CustomerLimitService;
import com.electron.mfs.pg.customer.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.customer.service.dto.CustomerLimitDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.customer.domain.CustomerLimit}.
 */
@RestController
@RequestMapping("/api")
public class CustomerLimitResource {

    private final Logger log = LoggerFactory.getLogger(CustomerLimitResource.class);

    private static final String ENTITY_NAME = "customerManagerCustomerLimit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerLimitService customerLimitService;

    public CustomerLimitResource(CustomerLimitService customerLimitService) {
        this.customerLimitService = customerLimitService;
    }

    /**
     * {@code POST  /customer-limits} : Create a new customerLimit.
     *
     * @param customerLimitDTO the customerLimitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerLimitDTO, or with status {@code 400 (Bad Request)} if the customerLimit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-limits")
    public ResponseEntity<CustomerLimitDTO> createCustomerLimit(@Valid @RequestBody CustomerLimitDTO customerLimitDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerLimit : {}", customerLimitDTO);
        if (customerLimitDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerLimit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerLimitDTO result = customerLimitService.save(customerLimitDTO);
        return ResponseEntity.created(new URI("/api/customer-limits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-limits} : Updates an existing customerLimit.
     *
     * @param customerLimitDTO the customerLimitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerLimitDTO,
     * or with status {@code 400 (Bad Request)} if the customerLimitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerLimitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-limits")
    public ResponseEntity<CustomerLimitDTO> updateCustomerLimit(@Valid @RequestBody CustomerLimitDTO customerLimitDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerLimit : {}", customerLimitDTO);
        if (customerLimitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerLimitDTO result = customerLimitService.save(customerLimitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerLimitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-limits} : get all the customerLimits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerLimits in body.
     */
    @GetMapping("/customer-limits")
    public List<CustomerLimitDTO> getAllCustomerLimits() {
        log.debug("REST request to get all CustomerLimits");
        return customerLimitService.findAll();
    }

    /**
     * {@code GET  /customer-limits/:id} : get the "id" customerLimit.
     *
     * @param id the id of the customerLimitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerLimitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-limits/{id}")
    public ResponseEntity<CustomerLimitDTO> getCustomerLimit(@PathVariable Long id) {
        log.debug("REST request to get CustomerLimit : {}", id);
        Optional<CustomerLimitDTO> customerLimitDTO = customerLimitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerLimitDTO);
    }

    /**
     * {@code DELETE  /customer-limits/:id} : delete the "id" customerLimit.
     *
     * @param id the id of the customerLimitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-limits/{id}")
    public ResponseEntity<Void> deleteCustomerLimit(@PathVariable Long id) {
        log.debug("REST request to delete CustomerLimit : {}", id);
        customerLimitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
