package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.service.AccountFeatureService;
import com.electron.mfs.pg.account.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.account.service.dto.AccountFeatureDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.account.domain.AccountFeature}.
 */
@RestController
@RequestMapping("/api")
public class AccountFeatureResource {

    private final Logger log = LoggerFactory.getLogger(AccountFeatureResource.class);

    private static final String ENTITY_NAME = "accountManagerAccountFeature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountFeatureService accountFeatureService;

    public AccountFeatureResource(AccountFeatureService accountFeatureService) {
        this.accountFeatureService = accountFeatureService;
    }

    /**
     * {@code POST  /account-features} : Create a new accountFeature.
     *
     * @param accountFeatureDTO the accountFeatureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountFeatureDTO, or with status {@code 400 (Bad Request)} if the accountFeature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-features")
    public ResponseEntity<AccountFeatureDTO> createAccountFeature(@Valid @RequestBody AccountFeatureDTO accountFeatureDTO) throws URISyntaxException {
        log.debug("REST request to save AccountFeature : {}", accountFeatureDTO);
        if (accountFeatureDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountFeature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountFeatureDTO result = accountFeatureService.save(accountFeatureDTO);
        return ResponseEntity.created(new URI("/api/account-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-features} : Updates an existing accountFeature.
     *
     * @param accountFeatureDTO the accountFeatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountFeatureDTO,
     * or with status {@code 400 (Bad Request)} if the accountFeatureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountFeatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-features")
    public ResponseEntity<AccountFeatureDTO> updateAccountFeature(@Valid @RequestBody AccountFeatureDTO accountFeatureDTO) throws URISyntaxException {
        log.debug("REST request to update AccountFeature : {}", accountFeatureDTO);
        if (accountFeatureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountFeatureDTO result = accountFeatureService.save(accountFeatureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountFeatureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-features} : get all the accountFeatures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountFeatures in body.
     */
    @GetMapping("/account-features")
    public List<AccountFeatureDTO> getAllAccountFeatures() {
        log.debug("REST request to get all AccountFeatures");
        return accountFeatureService.findAll();
    }

    /**
     * {@code GET  /account-features/:id} : get the "id" accountFeature.
     *
     * @param id the id of the accountFeatureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountFeatureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-features/{id}")
    public ResponseEntity<AccountFeatureDTO> getAccountFeature(@PathVariable Long id) {
        log.debug("REST request to get AccountFeature : {}", id);
        Optional<AccountFeatureDTO> accountFeatureDTO = accountFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountFeatureDTO);
    }

    /**
     * {@code DELETE  /account-features/:id} : delete the "id" accountFeature.
     *
     * @param id the id of the accountFeatureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-features/{id}")
    public ResponseEntity<Void> deleteAccountFeature(@PathVariable Long id) {
        log.debug("REST request to delete AccountFeature : {}", id);
        accountFeatureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
