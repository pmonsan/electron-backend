package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.DetailTransactionService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.DetailTransactionDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.DetailTransaction}.
 */
@RestController
@RequestMapping("/api")
public class DetailTransactionResource {

    private final Logger log = LoggerFactory.getLogger(DetailTransactionResource.class);

    private static final String ENTITY_NAME = "detailTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailTransactionService detailTransactionService;

    public DetailTransactionResource(DetailTransactionService detailTransactionService) {
        this.detailTransactionService = detailTransactionService;
    }

    /**
     * {@code POST  /detail-transactions} : Create a new detailTransaction.
     *
     * @param detailTransactionDTO the detailTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailTransactionDTO, or with status {@code 400 (Bad Request)} if the detailTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-transactions")
    public ResponseEntity<DetailTransactionDTO> createDetailTransaction(@Valid @RequestBody DetailTransactionDTO detailTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save DetailTransaction : {}", detailTransactionDTO);
        if (detailTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new detailTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailTransactionDTO result = detailTransactionService.save(detailTransactionDTO);
        return ResponseEntity.created(new URI("/api/detail-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-transactions} : Updates an existing detailTransaction.
     *
     * @param detailTransactionDTO the detailTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the detailTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-transactions")
    public ResponseEntity<DetailTransactionDTO> updateDetailTransaction(@Valid @RequestBody DetailTransactionDTO detailTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update DetailTransaction : {}", detailTransactionDTO);
        if (detailTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetailTransactionDTO result = detailTransactionService.save(detailTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detail-transactions} : get all the detailTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailTransactions in body.
     */
    @GetMapping("/detail-transactions")
    public List<DetailTransactionDTO> getAllDetailTransactions() {
        log.debug("REST request to get all DetailTransactions");
        return detailTransactionService.findAll();
    }

    /**
     * {@code GET  /detail-transactions/:id} : get the "id" detailTransaction.
     *
     * @param id the id of the detailTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-transactions/{id}")
    public ResponseEntity<DetailTransactionDTO> getDetailTransaction(@PathVariable Long id) {
        log.debug("REST request to get DetailTransaction : {}", id);
        Optional<DetailTransactionDTO> detailTransactionDTO = detailTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailTransactionDTO);
    }

    /**
     * {@code DELETE  /detail-transactions/:id} : delete the "id" detailTransaction.
     *
     * @param id the id of the detailTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-transactions/{id}")
    public ResponseEntity<Void> deleteDetailTransaction(@PathVariable Long id) {
        log.debug("REST request to delete DetailTransaction : {}", id);
        detailTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
