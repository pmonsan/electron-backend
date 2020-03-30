package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.CustomerTypeService;
import com.electron.mfs.pg.mdm.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.mdm.service.dto.CustomerTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.mdm.domain.CustomerType}.
 */
@RestController
@RequestMapping("/api")
public class CustomerTypeResource {

    private final Logger log = LoggerFactory.getLogger(CustomerTypeResource.class);

    private static final String ENTITY_NAME = "mdmManagerCustomerType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerTypeService customerTypeService;

    public CustomerTypeResource(CustomerTypeService customerTypeService) {
        this.customerTypeService = customerTypeService;
    }

    /**
     * {@code POST  /customer-types} : Create a new customerType.
     *
     * @param customerTypeDTO the customerTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerTypeDTO, or with status {@code 400 (Bad Request)} if the customerType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-types")
    public ResponseEntity<CustomerTypeDTO> createCustomerType(@Valid @RequestBody CustomerTypeDTO customerTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerType : {}", customerTypeDTO);
        if (customerTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerTypeDTO result = customerTypeService.save(customerTypeDTO);
        return ResponseEntity.created(new URI("/api/customer-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-types} : Updates an existing customerType.
     *
     * @param customerTypeDTO the customerTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerTypeDTO,
     * or with status {@code 400 (Bad Request)} if the customerTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-types")
    public ResponseEntity<CustomerTypeDTO> updateCustomerType(@Valid @RequestBody CustomerTypeDTO customerTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerType : {}", customerTypeDTO);
        if (customerTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerTypeDTO result = customerTypeService.save(customerTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-types} : get all the customerTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerTypes in body.
     */
    @GetMapping("/customer-types")
    public List<CustomerTypeDTO> getAllCustomerTypes() {
        log.debug("REST request to get all CustomerTypes");
        return customerTypeService.findAll();
    }

    /**
     * {@code GET  /customer-types/:id} : get the "id" customerType.
     *
     * @param id the id of the customerTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-types/{id}")
    public ResponseEntity<CustomerTypeDTO> getCustomerType(@PathVariable Long id) {
        log.debug("REST request to get CustomerType : {}", id);
        Optional<CustomerTypeDTO> customerTypeDTO = customerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerTypeDTO);
    }

    /**
     * {@code DELETE  /customer-types/:id} : delete the "id" customerType.
     *
     * @param id the id of the customerTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-types/{id}")
    public ResponseEntity<Void> deleteCustomerType(@PathVariable Long id) {
        log.debug("REST request to delete CustomerType : {}", id);
        customerTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
