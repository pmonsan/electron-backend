package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PersonTypeService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PersonTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PersonType}.
 */
@RestController
@RequestMapping("/api")
public class PersonTypeResource {

    private final Logger log = LoggerFactory.getLogger(PersonTypeResource.class);

    private static final String ENTITY_NAME = "personType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonTypeService personTypeService;

    public PersonTypeResource(PersonTypeService personTypeService) {
        this.personTypeService = personTypeService;
    }

    /**
     * {@code POST  /person-types} : Create a new personType.
     *
     * @param personTypeDTO the personTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personTypeDTO, or with status {@code 400 (Bad Request)} if the personType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-types")
    public ResponseEntity<PersonTypeDTO> createPersonType(@Valid @RequestBody PersonTypeDTO personTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PersonType : {}", personTypeDTO);
        if (personTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new personType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonTypeDTO result = personTypeService.save(personTypeDTO);
        return ResponseEntity.created(new URI("/api/person-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-types} : Updates an existing personType.
     *
     * @param personTypeDTO the personTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personTypeDTO,
     * or with status {@code 400 (Bad Request)} if the personTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-types")
    public ResponseEntity<PersonTypeDTO> updatePersonType(@Valid @RequestBody PersonTypeDTO personTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PersonType : {}", personTypeDTO);
        if (personTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonTypeDTO result = personTypeService.save(personTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-types} : get all the personTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personTypes in body.
     */
    @GetMapping("/person-types")
    public List<PersonTypeDTO> getAllPersonTypes() {
        log.debug("REST request to get all PersonTypes");
        return personTypeService.findAll();
    }

    /**
     * {@code GET  /person-types/:id} : get the "id" personType.
     *
     * @param id the id of the personTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-types/{id}")
    public ResponseEntity<PersonTypeDTO> getPersonType(@PathVariable Long id) {
        log.debug("REST request to get PersonType : {}", id);
        Optional<PersonTypeDTO> personTypeDTO = personTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personTypeDTO);
    }

    /**
     * {@code DELETE  /person-types/:id} : delete the "id" personType.
     *
     * @param id the id of the personTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-types/{id}")
    public ResponseEntity<Void> deletePersonType(@PathVariable Long id) {
        log.debug("REST request to delete PersonType : {}", id);
        personTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
