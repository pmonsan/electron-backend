package com.electron.mfs.pg.limits.web.rest;

import com.electron.mfs.pg.limits.service.LimitTypeService;
import com.electron.mfs.pg.limits.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.limits.service.dto.LimitTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.limits.domain.LimitType}.
 */
@RestController
@RequestMapping("/api")
public class LimitTypeResource {

    private final Logger log = LoggerFactory.getLogger(LimitTypeResource.class);

    private static final String ENTITY_NAME = "limitsManagerLimitType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LimitTypeService limitTypeService;

    public LimitTypeResource(LimitTypeService limitTypeService) {
        this.limitTypeService = limitTypeService;
    }

    /**
     * {@code POST  /limit-types} : Create a new limitType.
     *
     * @param limitTypeDTO the limitTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new limitTypeDTO, or with status {@code 400 (Bad Request)} if the limitType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/limit-types")
    public ResponseEntity<LimitTypeDTO> createLimitType(@Valid @RequestBody LimitTypeDTO limitTypeDTO) throws URISyntaxException {
        log.debug("REST request to save LimitType : {}", limitTypeDTO);
        if (limitTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new limitType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LimitTypeDTO result = limitTypeService.save(limitTypeDTO);
        return ResponseEntity.created(new URI("/api/limit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /limit-types} : Updates an existing limitType.
     *
     * @param limitTypeDTO the limitTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated limitTypeDTO,
     * or with status {@code 400 (Bad Request)} if the limitTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the limitTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/limit-types")
    public ResponseEntity<LimitTypeDTO> updateLimitType(@Valid @RequestBody LimitTypeDTO limitTypeDTO) throws URISyntaxException {
        log.debug("REST request to update LimitType : {}", limitTypeDTO);
        if (limitTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LimitTypeDTO result = limitTypeService.save(limitTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, limitTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /limit-types} : get all the limitTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of limitTypes in body.
     */
    @GetMapping("/limit-types")
    public List<LimitTypeDTO> getAllLimitTypes() {
        log.debug("REST request to get all LimitTypes");
        return limitTypeService.findAll();
    }

    /**
     * {@code GET  /limit-types/:id} : get the "id" limitType.
     *
     * @param id the id of the limitTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the limitTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/limit-types/{id}")
    public ResponseEntity<LimitTypeDTO> getLimitType(@PathVariable Long id) {
        log.debug("REST request to get LimitType : {}", id);
        Optional<LimitTypeDTO> limitTypeDTO = limitTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(limitTypeDTO);
    }

    /**
     * {@code DELETE  /limit-types/:id} : delete the "id" limitType.
     *
     * @param id the id of the limitTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/limit-types/{id}")
    public ResponseEntity<Void> deleteLimitType(@PathVariable Long id) {
        log.debug("REST request to delete LimitType : {}", id);
        limitTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
