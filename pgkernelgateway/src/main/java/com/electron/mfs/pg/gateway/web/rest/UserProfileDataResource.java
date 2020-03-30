package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.UserProfileDataService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.UserProfileDataDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.UserProfileData}.
 */
@RestController
@RequestMapping("/api")
public class UserProfileDataResource {

    private final Logger log = LoggerFactory.getLogger(UserProfileDataResource.class);

    private static final String ENTITY_NAME = "userProfileData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserProfileDataService userProfileDataService;

    public UserProfileDataResource(UserProfileDataService userProfileDataService) {
        this.userProfileDataService = userProfileDataService;
    }

    /**
     * {@code POST  /user-profile-data} : Create a new userProfileData.
     *
     * @param userProfileDataDTO the userProfileDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userProfileDataDTO, or with status {@code 400 (Bad Request)} if the userProfileData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-profile-data")
    public ResponseEntity<UserProfileDataDTO> createUserProfileData(@Valid @RequestBody UserProfileDataDTO userProfileDataDTO) throws URISyntaxException {
        log.debug("REST request to save UserProfileData : {}", userProfileDataDTO);
        if (userProfileDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new userProfileData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserProfileDataDTO result = userProfileDataService.save(userProfileDataDTO);
        return ResponseEntity.created(new URI("/api/user-profile-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-profile-data} : Updates an existing userProfileData.
     *
     * @param userProfileDataDTO the userProfileDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userProfileDataDTO,
     * or with status {@code 400 (Bad Request)} if the userProfileDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userProfileDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-profile-data")
    public ResponseEntity<UserProfileDataDTO> updateUserProfileData(@Valid @RequestBody UserProfileDataDTO userProfileDataDTO) throws URISyntaxException {
        log.debug("REST request to update UserProfileData : {}", userProfileDataDTO);
        if (userProfileDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserProfileDataDTO result = userProfileDataService.save(userProfileDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userProfileDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-profile-data} : get all the userProfileData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userProfileData in body.
     */
    @GetMapping("/user-profile-data")
    public List<UserProfileDataDTO> getAllUserProfileData() {
        log.debug("REST request to get all UserProfileData");
        return userProfileDataService.findAll();
    }

    /**
     * {@code GET  /user-profile-data/:id} : get the "id" userProfileData.
     *
     * @param id the id of the userProfileDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userProfileDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-profile-data/{id}")
    public ResponseEntity<UserProfileDataDTO> getUserProfileData(@PathVariable Long id) {
        log.debug("REST request to get UserProfileData : {}", id);
        Optional<UserProfileDataDTO> userProfileDataDTO = userProfileDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userProfileDataDTO);
    }

    /**
     * {@code DELETE  /user-profile-data/:id} : delete the "id" userProfileData.
     *
     * @param id the id of the userProfileDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-profile-data/{id}")
    public ResponseEntity<Void> deleteUserProfileData(@PathVariable Long id) {
        log.debug("REST request to delete UserProfileData : {}", id);
        userProfileDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
