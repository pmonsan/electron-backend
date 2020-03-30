package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PersonGenderService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.PersonGenderDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.PersonGender}.
 */
@RestController
@RequestMapping("/api")
public class PersonGenderResource {

    private final Logger log = LoggerFactory.getLogger(PersonGenderResource.class);

    private static final String ENTITY_NAME = "personGender";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonGenderService personGenderService;

    public PersonGenderResource(PersonGenderService personGenderService) {
        this.personGenderService = personGenderService;
    }

    /**
     * {@code POST  /person-genders} : Create a new personGender.
     *
     * @param personGenderDTO the personGenderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personGenderDTO, or with status {@code 400 (Bad Request)} if the personGender has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-genders")
    public ResponseEntity<PersonGenderDTO> createPersonGender(@Valid @RequestBody PersonGenderDTO personGenderDTO) throws URISyntaxException {
        log.debug("REST request to save PersonGender : {}", personGenderDTO);
        if (personGenderDTO.getId() != null) {
            throw new BadRequestAlertException("A new personGender cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonGenderDTO result = personGenderService.save(personGenderDTO);
        return ResponseEntity.created(new URI("/api/person-genders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-genders} : Updates an existing personGender.
     *
     * @param personGenderDTO the personGenderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personGenderDTO,
     * or with status {@code 400 (Bad Request)} if the personGenderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personGenderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-genders")
    public ResponseEntity<PersonGenderDTO> updatePersonGender(@Valid @RequestBody PersonGenderDTO personGenderDTO) throws URISyntaxException {
        log.debug("REST request to update PersonGender : {}", personGenderDTO);
        if (personGenderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonGenderDTO result = personGenderService.save(personGenderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personGenderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-genders} : get all the personGenders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personGenders in body.
     */
    @GetMapping("/person-genders")
    public List<PersonGenderDTO> getAllPersonGenders() {
        log.debug("REST request to get all PersonGenders");
        return personGenderService.findAll();
    }

    /**
     * {@code GET  /person-genders/:id} : get the "id" personGender.
     *
     * @param id the id of the personGenderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personGenderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-genders/{id}")
    public ResponseEntity<PersonGenderDTO> getPersonGender(@PathVariable Long id) {
        log.debug("REST request to get PersonGender : {}", id);
        Optional<PersonGenderDTO> personGenderDTO = personGenderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personGenderDTO);
    }

    /**
     * {@code DELETE  /person-genders/:id} : delete the "id" personGender.
     *
     * @param id the id of the personGenderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-genders/{id}")
    public ResponseEntity<Void> deletePersonGender(@PathVariable Long id) {
        log.debug("REST request to delete PersonGender : {}", id);
        personGenderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
