package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.service.DetailContractService;
import com.electron.mfs.pg.account.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.account.service.dto.DetailContractDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.account.domain.DetailContract}.
 */
@RestController
@RequestMapping("/api")
public class DetailContractResource {

    private final Logger log = LoggerFactory.getLogger(DetailContractResource.class);

    private static final String ENTITY_NAME = "accountManagerDetailContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailContractService detailContractService;

    public DetailContractResource(DetailContractService detailContractService) {
        this.detailContractService = detailContractService;
    }

    /**
     * {@code POST  /detail-contracts} : Create a new detailContract.
     *
     * @param detailContractDTO the detailContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailContractDTO, or with status {@code 400 (Bad Request)} if the detailContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-contracts")
    public ResponseEntity<DetailContractDTO> createDetailContract(@Valid @RequestBody DetailContractDTO detailContractDTO) throws URISyntaxException {
        log.debug("REST request to save DetailContract : {}", detailContractDTO);
        if (detailContractDTO.getId() != null) {
            throw new BadRequestAlertException("A new detailContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailContractDTO result = detailContractService.save(detailContractDTO);
        return ResponseEntity.created(new URI("/api/detail-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-contracts} : Updates an existing detailContract.
     *
     * @param detailContractDTO the detailContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailContractDTO,
     * or with status {@code 400 (Bad Request)} if the detailContractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-contracts")
    public ResponseEntity<DetailContractDTO> updateDetailContract(@Valid @RequestBody DetailContractDTO detailContractDTO) throws URISyntaxException {
        log.debug("REST request to update DetailContract : {}", detailContractDTO);
        if (detailContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetailContractDTO result = detailContractService.save(detailContractDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailContractDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detail-contracts} : get all the detailContracts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailContracts in body.
     */
    @GetMapping("/detail-contracts")
    public List<DetailContractDTO> getAllDetailContracts() {
        log.debug("REST request to get all DetailContracts");
        return detailContractService.findAll();
    }

    /**
     * {@code GET  /detail-contracts/:id} : get the "id" detailContract.
     *
     * @param id the id of the detailContractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailContractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-contracts/{id}")
    public ResponseEntity<DetailContractDTO> getDetailContract(@PathVariable Long id) {
        log.debug("REST request to get DetailContract : {}", id);
        Optional<DetailContractDTO> detailContractDTO = detailContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailContractDTO);
    }

    /**
     * {@code DELETE  /detail-contracts/:id} : delete the "id" detailContract.
     *
     * @param id the id of the detailContractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-contracts/{id}")
    public ResponseEntity<Void> deleteDetailContract(@PathVariable Long id) {
        log.debug("REST request to delete DetailContract : {}", id);
        detailContractService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
