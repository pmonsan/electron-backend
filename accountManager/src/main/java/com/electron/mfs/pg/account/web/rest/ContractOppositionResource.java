package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.service.ContractOppositionService;
import com.electron.mfs.pg.account.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.account.service.dto.ContractOppositionDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.account.domain.ContractOpposition}.
 */
@RestController
@RequestMapping("/api")
public class ContractOppositionResource {

    private final Logger log = LoggerFactory.getLogger(ContractOppositionResource.class);

    private static final String ENTITY_NAME = "accountManagerContractOpposition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractOppositionService contractOppositionService;

    public ContractOppositionResource(ContractOppositionService contractOppositionService) {
        this.contractOppositionService = contractOppositionService;
    }

    /**
     * {@code POST  /contract-oppositions} : Create a new contractOpposition.
     *
     * @param contractOppositionDTO the contractOppositionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractOppositionDTO, or with status {@code 400 (Bad Request)} if the contractOpposition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-oppositions")
    public ResponseEntity<ContractOppositionDTO> createContractOpposition(@Valid @RequestBody ContractOppositionDTO contractOppositionDTO) throws URISyntaxException {
        log.debug("REST request to save ContractOpposition : {}", contractOppositionDTO);
        if (contractOppositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new contractOpposition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractOppositionDTO result = contractOppositionService.save(contractOppositionDTO);
        return ResponseEntity.created(new URI("/api/contract-oppositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contract-oppositions} : Updates an existing contractOpposition.
     *
     * @param contractOppositionDTO the contractOppositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractOppositionDTO,
     * or with status {@code 400 (Bad Request)} if the contractOppositionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractOppositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-oppositions")
    public ResponseEntity<ContractOppositionDTO> updateContractOpposition(@Valid @RequestBody ContractOppositionDTO contractOppositionDTO) throws URISyntaxException {
        log.debug("REST request to update ContractOpposition : {}", contractOppositionDTO);
        if (contractOppositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContractOppositionDTO result = contractOppositionService.save(contractOppositionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractOppositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contract-oppositions} : get all the contractOppositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractOppositions in body.
     */
    @GetMapping("/contract-oppositions")
    public List<ContractOppositionDTO> getAllContractOppositions() {
        log.debug("REST request to get all ContractOppositions");
        return contractOppositionService.findAll();
    }

    /**
     * {@code GET  /contract-oppositions/:id} : get the "id" contractOpposition.
     *
     * @param id the id of the contractOppositionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractOppositionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-oppositions/{id}")
    public ResponseEntity<ContractOppositionDTO> getContractOpposition(@PathVariable Long id) {
        log.debug("REST request to get ContractOpposition : {}", id);
        Optional<ContractOppositionDTO> contractOppositionDTO = contractOppositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contractOppositionDTO);
    }

    /**
     * {@code DELETE  /contract-oppositions/:id} : delete the "id" contractOpposition.
     *
     * @param id the id of the contractOppositionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-oppositions/{id}")
    public ResponseEntity<Void> deleteContractOpposition(@PathVariable Long id) {
        log.debug("REST request to delete ContractOpposition : {}", id);
        contractOppositionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
