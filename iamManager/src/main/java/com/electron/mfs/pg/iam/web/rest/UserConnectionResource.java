package com.electron.mfs.pg.iam.web.rest;

import com.electron.mfs.pg.iam.service.UserConnectionService;
import com.electron.mfs.pg.iam.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.iam.service.dto.UserConnectionDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.iam.domain.UserConnection}.
 */
@RestController
@RequestMapping("/api")
public class UserConnectionResource {

    private final Logger log = LoggerFactory.getLogger(UserConnectionResource.class);

    private static final String ENTITY_NAME = "iamManagerUserConnection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserConnectionService userConnectionService;

    public UserConnectionResource(UserConnectionService userConnectionService) {
        this.userConnectionService = userConnectionService;
    }

    /**
     * {@code POST  /user-connections} : Create a new userConnection.
     *
     * @param userConnectionDTO the userConnectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userConnectionDTO, or with status {@code 400 (Bad Request)} if the userConnection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-connections")
    public ResponseEntity<UserConnectionDTO> createUserConnection(@Valid @RequestBody UserConnectionDTO userConnectionDTO) throws URISyntaxException {
        log.debug("REST request to save UserConnection : {}", userConnectionDTO);
        if (userConnectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new userConnection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserConnectionDTO result = userConnectionService.save(userConnectionDTO);
        return ResponseEntity.created(new URI("/api/user-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-connections} : Updates an existing userConnection.
     *
     * @param userConnectionDTO the userConnectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userConnectionDTO,
     * or with status {@code 400 (Bad Request)} if the userConnectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userConnectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-connections")
    public ResponseEntity<UserConnectionDTO> updateUserConnection(@Valid @RequestBody UserConnectionDTO userConnectionDTO) throws URISyntaxException {
        log.debug("REST request to update UserConnection : {}", userConnectionDTO);
        if (userConnectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserConnectionDTO result = userConnectionService.save(userConnectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userConnectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-connections} : get all the userConnections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userConnections in body.
     */
    @GetMapping("/user-connections")
    public List<UserConnectionDTO> getAllUserConnections() {
        log.debug("REST request to get all UserConnections");
        return userConnectionService.findAll();
    }

    /**
     * {@code GET  /user-connections/:id} : get the "id" userConnection.
     *
     * @param id the id of the userConnectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userConnectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-connections/{id}")
    public ResponseEntity<UserConnectionDTO> getUserConnection(@PathVariable Long id) {
        log.debug("REST request to get UserConnection : {}", id);
        Optional<UserConnectionDTO> userConnectionDTO = userConnectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userConnectionDTO);
    }

    /**
     * {@code DELETE  /user-connections/:id} : delete the "id" userConnection.
     *
     * @param id the id of the userConnectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-connections/{id}")
    public ResponseEntity<Void> deleteUserConnection(@PathVariable Long id) {
        log.debug("REST request to delete UserConnection : {}", id);
        userConnectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
