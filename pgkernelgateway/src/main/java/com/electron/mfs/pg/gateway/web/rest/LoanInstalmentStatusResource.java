package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.LoanInstalmentStatusService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.LoanInstalmentStatusDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.LoanInstalmentStatus}.
 */
@RestController
@RequestMapping("/api")
public class LoanInstalmentStatusResource {

    private final Logger log = LoggerFactory.getLogger(LoanInstalmentStatusResource.class);

    private static final String ENTITY_NAME = "loanInstalmentStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanInstalmentStatusService loanInstalmentStatusService;

    public LoanInstalmentStatusResource(LoanInstalmentStatusService loanInstalmentStatusService) {
        this.loanInstalmentStatusService = loanInstalmentStatusService;
    }

    /**
     * {@code POST  /loan-instalment-statuses} : Create a new loanInstalmentStatus.
     *
     * @param loanInstalmentStatusDTO the loanInstalmentStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanInstalmentStatusDTO, or with status {@code 400 (Bad Request)} if the loanInstalmentStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-instalment-statuses")
    public ResponseEntity<LoanInstalmentStatusDTO> createLoanInstalmentStatus(@Valid @RequestBody LoanInstalmentStatusDTO loanInstalmentStatusDTO) throws URISyntaxException {
        log.debug("REST request to save LoanInstalmentStatus : {}", loanInstalmentStatusDTO);
        if (loanInstalmentStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanInstalmentStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanInstalmentStatusDTO result = loanInstalmentStatusService.save(loanInstalmentStatusDTO);
        return ResponseEntity.created(new URI("/api/loan-instalment-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-instalment-statuses} : Updates an existing loanInstalmentStatus.
     *
     * @param loanInstalmentStatusDTO the loanInstalmentStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanInstalmentStatusDTO,
     * or with status {@code 400 (Bad Request)} if the loanInstalmentStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanInstalmentStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-instalment-statuses")
    public ResponseEntity<LoanInstalmentStatusDTO> updateLoanInstalmentStatus(@Valid @RequestBody LoanInstalmentStatusDTO loanInstalmentStatusDTO) throws URISyntaxException {
        log.debug("REST request to update LoanInstalmentStatus : {}", loanInstalmentStatusDTO);
        if (loanInstalmentStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoanInstalmentStatusDTO result = loanInstalmentStatusService.save(loanInstalmentStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanInstalmentStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /loan-instalment-statuses} : get all the loanInstalmentStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanInstalmentStatuses in body.
     */
    @GetMapping("/loan-instalment-statuses")
    public List<LoanInstalmentStatusDTO> getAllLoanInstalmentStatuses() {
        log.debug("REST request to get all LoanInstalmentStatuses");
        return loanInstalmentStatusService.findAll();
    }

    /**
     * {@code GET  /loan-instalment-statuses/:id} : get the "id" loanInstalmentStatus.
     *
     * @param id the id of the loanInstalmentStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanInstalmentStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-instalment-statuses/{id}")
    public ResponseEntity<LoanInstalmentStatusDTO> getLoanInstalmentStatus(@PathVariable Long id) {
        log.debug("REST request to get LoanInstalmentStatus : {}", id);
        Optional<LoanInstalmentStatusDTO> loanInstalmentStatusDTO = loanInstalmentStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanInstalmentStatusDTO);
    }

    /**
     * {@code DELETE  /loan-instalment-statuses/:id} : delete the "id" loanInstalmentStatus.
     *
     * @param id the id of the loanInstalmentStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-instalment-statuses/{id}")
    public ResponseEntity<Void> deleteLoanInstalmentStatus(@PathVariable Long id) {
        log.debug("REST request to delete LoanInstalmentStatus : {}", id);
        loanInstalmentStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
