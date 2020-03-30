package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.CustomerBlacklistService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.CustomerBlacklistDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.CustomerBlacklist}.
 */
@RestController
@RequestMapping("/api")
public class CustomerBlacklistResource {

    private final Logger log = LoggerFactory.getLogger(CustomerBlacklistResource.class);

    private static final String ENTITY_NAME = "customerBlacklist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerBlacklistService customerBlacklistService;

    public CustomerBlacklistResource(CustomerBlacklistService customerBlacklistService) {
        this.customerBlacklistService = customerBlacklistService;
    }

    /**
     * {@code POST  /customer-blacklists} : Create a new customerBlacklist.
     *
     * @param customerBlacklistDTO the customerBlacklistDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerBlacklistDTO, or with status {@code 400 (Bad Request)} if the customerBlacklist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-blacklists")
    public ResponseEntity<CustomerBlacklistDTO> createCustomerBlacklist(@Valid @RequestBody CustomerBlacklistDTO customerBlacklistDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerBlacklist : {}", customerBlacklistDTO);
        if (customerBlacklistDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerBlacklist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerBlacklistDTO result = customerBlacklistService.save(customerBlacklistDTO);
        return ResponseEntity.created(new URI("/api/customer-blacklists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-blacklists} : Updates an existing customerBlacklist.
     *
     * @param customerBlacklistDTO the customerBlacklistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerBlacklistDTO,
     * or with status {@code 400 (Bad Request)} if the customerBlacklistDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerBlacklistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-blacklists")
    public ResponseEntity<CustomerBlacklistDTO> updateCustomerBlacklist(@Valid @RequestBody CustomerBlacklistDTO customerBlacklistDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerBlacklist : {}", customerBlacklistDTO);
        if (customerBlacklistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerBlacklistDTO result = customerBlacklistService.save(customerBlacklistDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerBlacklistDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-blacklists} : get all the customerBlacklists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerBlacklists in body.
     */
    @GetMapping("/customer-blacklists")
    public List<CustomerBlacklistDTO> getAllCustomerBlacklists() {
        log.debug("REST request to get all CustomerBlacklists");
        return customerBlacklistService.findAll();
    }

    /**
     * {@code GET  /customer-blacklists/:id} : get the "id" customerBlacklist.
     *
     * @param id the id of the customerBlacklistDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerBlacklistDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-blacklists/{id}")
    public ResponseEntity<CustomerBlacklistDTO> getCustomerBlacklist(@PathVariable Long id) {
        log.debug("REST request to get CustomerBlacklist : {}", id);
        Optional<CustomerBlacklistDTO> customerBlacklistDTO = customerBlacklistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerBlacklistDTO);
    }

    /**
     * {@code DELETE  /customer-blacklists/:id} : delete the "id" customerBlacklist.
     *
     * @param id the id of the customerBlacklistDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-blacklists/{id}")
    public ResponseEntity<Void> deleteCustomerBlacklist(@PathVariable Long id) {
        log.debug("REST request to delete CustomerBlacklist : {}", id);
        customerBlacklistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
