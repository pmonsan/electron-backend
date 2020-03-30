package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.MeansofpaymentService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.Meansofpayment}.
 */
@RestController
@RequestMapping("/api")
public class MeansofpaymentResource {

    private final Logger log = LoggerFactory.getLogger(MeansofpaymentResource.class);

    private static final String ENTITY_NAME = "meansofpayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeansofpaymentService meansofpaymentService;

    public MeansofpaymentResource(MeansofpaymentService meansofpaymentService) {
        this.meansofpaymentService = meansofpaymentService;
    }

    /**
     * {@code POST  /meansofpayments} : Create a new meansofpayment.
     *
     * @param meansofpaymentDTO the meansofpaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meansofpaymentDTO, or with status {@code 400 (Bad Request)} if the meansofpayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meansofpayments")
    public ResponseEntity<MeansofpaymentDTO> createMeansofpayment(@Valid @RequestBody MeansofpaymentDTO meansofpaymentDTO) throws URISyntaxException {
        log.debug("REST request to save Meansofpayment : {}", meansofpaymentDTO);
        if (meansofpaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new meansofpayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeansofpaymentDTO result = meansofpaymentService.save(meansofpaymentDTO);
        return ResponseEntity.created(new URI("/api/meansofpayments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meansofpayments} : Updates an existing meansofpayment.
     *
     * @param meansofpaymentDTO the meansofpaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meansofpaymentDTO,
     * or with status {@code 400 (Bad Request)} if the meansofpaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meansofpaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meansofpayments")
    public ResponseEntity<MeansofpaymentDTO> updateMeansofpayment(@Valid @RequestBody MeansofpaymentDTO meansofpaymentDTO) throws URISyntaxException {
        log.debug("REST request to update Meansofpayment : {}", meansofpaymentDTO);
        if (meansofpaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeansofpaymentDTO result = meansofpaymentService.save(meansofpaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meansofpaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meansofpayments} : get all the meansofpayments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meansofpayments in body.
     */
    @GetMapping("/meansofpayments")
    public List<MeansofpaymentDTO> getAllMeansofpayments() {
        log.debug("REST request to get all Meansofpayments");
        return meansofpaymentService.findAll();
    }

    /**
     * {@code GET  /meansofpayments/:id} : get the "id" meansofpayment.
     *
     * @param id the id of the meansofpaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meansofpaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meansofpayments/{id}")
    public ResponseEntity<MeansofpaymentDTO> getMeansofpayment(@PathVariable Long id) {
        log.debug("REST request to get Meansofpayment : {}", id);
        Optional<MeansofpaymentDTO> meansofpaymentDTO = meansofpaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meansofpaymentDTO);
    }

    /**
     * {@code DELETE  /meansofpayments/:id} : delete the "id" meansofpayment.
     *
     * @param id the id of the meansofpaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meansofpayments/{id}")
    public ResponseEntity<Void> deleteMeansofpayment(@PathVariable Long id) {
        log.debug("REST request to delete Meansofpayment : {}", id);
        meansofpaymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
