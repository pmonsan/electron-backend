package com.electron.mfs.pg.transactions.web.rest;

import com.electron.mfs.pg.transactions.service.LoanInstalmentService;
import com.electron.mfs.pg.transactions.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.transactions.service.dto.LoanInstalmentDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.transactions.domain.LoanInstalment}.
 */
@RestController
@RequestMapping("/api")
public class LoanInstalmentResource {

    private final Logger log = LoggerFactory.getLogger(LoanInstalmentResource.class);

    private static final String ENTITY_NAME = "transactionManagerLoanInstalment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanInstalmentService loanInstalmentService;

    public LoanInstalmentResource(LoanInstalmentService loanInstalmentService) {
        this.loanInstalmentService = loanInstalmentService;
    }

    /**
     * {@code POST  /loan-instalments} : Create a new loanInstalment.
     *
     * @param loanInstalmentDTO the loanInstalmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanInstalmentDTO, or with status {@code 400 (Bad Request)} if the loanInstalment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-instalments")
    public ResponseEntity<LoanInstalmentDTO> createLoanInstalment(@Valid @RequestBody LoanInstalmentDTO loanInstalmentDTO) throws URISyntaxException {
        log.debug("REST request to save LoanInstalment : {}", loanInstalmentDTO);
        if (loanInstalmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanInstalment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanInstalmentDTO result = loanInstalmentService.save(loanInstalmentDTO);
        return ResponseEntity.created(new URI("/api/loan-instalments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-instalments} : Updates an existing loanInstalment.
     *
     * @param loanInstalmentDTO the loanInstalmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanInstalmentDTO,
     * or with status {@code 400 (Bad Request)} if the loanInstalmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanInstalmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-instalments")
    public ResponseEntity<LoanInstalmentDTO> updateLoanInstalment(@Valid @RequestBody LoanInstalmentDTO loanInstalmentDTO) throws URISyntaxException {
        log.debug("REST request to update LoanInstalment : {}", loanInstalmentDTO);
        if (loanInstalmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoanInstalmentDTO result = loanInstalmentService.save(loanInstalmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanInstalmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /loan-instalments} : get all the loanInstalments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanInstalments in body.
     */
    @GetMapping("/loan-instalments")
    public List<LoanInstalmentDTO> getAllLoanInstalments() {
        log.debug("REST request to get all LoanInstalments");
        return loanInstalmentService.findAll();
    }

    /**
     * {@code GET  /loan-instalments/:id} : get the "id" loanInstalment.
     *
     * @param id the id of the loanInstalmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanInstalmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-instalments/{id}")
    public ResponseEntity<LoanInstalmentDTO> getLoanInstalment(@PathVariable Long id) {
        log.debug("REST request to get LoanInstalment : {}", id);
        Optional<LoanInstalmentDTO> loanInstalmentDTO = loanInstalmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanInstalmentDTO);
    }

    /**
     * {@code DELETE  /loan-instalments/:id} : delete the "id" loanInstalment.
     *
     * @param id the id of the loanInstalmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-instalments/{id}")
    public ResponseEntity<Void> deleteLoanInstalment(@PathVariable Long id) {
        log.debug("REST request to delete LoanInstalment : {}", id);
        loanInstalmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
