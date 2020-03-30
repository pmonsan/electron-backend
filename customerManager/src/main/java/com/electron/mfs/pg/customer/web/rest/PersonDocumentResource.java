package com.electron.mfs.pg.customer.web.rest;

import com.electron.mfs.pg.customer.service.PersonDocumentService;
import com.electron.mfs.pg.customer.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.customer.service.dto.PersonDocumentDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.customer.domain.PersonDocument}.
 */
@RestController
@RequestMapping("/api")
public class PersonDocumentResource {

    private final Logger log = LoggerFactory.getLogger(PersonDocumentResource.class);

    private static final String ENTITY_NAME = "customerManagerPersonDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonDocumentService personDocumentService;

    public PersonDocumentResource(PersonDocumentService personDocumentService) {
        this.personDocumentService = personDocumentService;
    }

    /**
     * {@code POST  /person-documents} : Create a new personDocument.
     *
     * @param personDocumentDTO the personDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personDocumentDTO, or with status {@code 400 (Bad Request)} if the personDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-documents")
    public ResponseEntity<PersonDocumentDTO> createPersonDocument(@Valid @RequestBody PersonDocumentDTO personDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save PersonDocument : {}", personDocumentDTO);
        if (personDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new personDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonDocumentDTO result = personDocumentService.save(personDocumentDTO);
        return ResponseEntity.created(new URI("/api/person-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-documents} : Updates an existing personDocument.
     *
     * @param personDocumentDTO the personDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the personDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-documents")
    public ResponseEntity<PersonDocumentDTO> updatePersonDocument(@Valid @RequestBody PersonDocumentDTO personDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update PersonDocument : {}", personDocumentDTO);
        if (personDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonDocumentDTO result = personDocumentService.save(personDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-documents} : get all the personDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personDocuments in body.
     */
    @GetMapping("/person-documents")
    public List<PersonDocumentDTO> getAllPersonDocuments() {
        log.debug("REST request to get all PersonDocuments");
        return personDocumentService.findAll();
    }

    /**
     * {@code GET  /person-documents/:id} : get the "id" personDocument.
     *
     * @param id the id of the personDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-documents/{id}")
    public ResponseEntity<PersonDocumentDTO> getPersonDocument(@PathVariable Long id) {
        log.debug("REST request to get PersonDocument : {}", id);
        Optional<PersonDocumentDTO> personDocumentDTO = personDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personDocumentDTO);
    }

    /**
     * {@code DELETE  /person-documents/:id} : delete the "id" personDocument.
     *
     * @param id the id of the personDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-documents/{id}")
    public ResponseEntity<Void> deletePersonDocument(@PathVariable Long id) {
        log.debug("REST request to delete PersonDocument : {}", id);
        personDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
