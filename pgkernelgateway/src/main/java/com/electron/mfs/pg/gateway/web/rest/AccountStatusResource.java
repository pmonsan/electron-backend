package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.AccountStatusService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.AccountStatusDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.AccountStatus}.
 */
@RestController
@RequestMapping("/api")
public class AccountStatusResource {

    private final Logger log = LoggerFactory.getLogger(AccountStatusResource.class);

    private static final String ENTITY_NAME = "accountStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountStatusService accountStatusService;

    public AccountStatusResource(AccountStatusService accountStatusService) {
        this.accountStatusService = accountStatusService;
    }

    /**
     * {@code POST  /account-statuses} : Create a new accountStatus.
     *
     * @param accountStatusDTO the accountStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountStatusDTO, or with status {@code 400 (Bad Request)} if the accountStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-statuses")
    public ResponseEntity<AccountStatusDTO> createAccountStatus(@Valid @RequestBody AccountStatusDTO accountStatusDTO) throws URISyntaxException {
        log.debug("REST request to save AccountStatus : {}", accountStatusDTO);
        if (accountStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountStatusDTO result = accountStatusService.save(accountStatusDTO);
        return ResponseEntity.created(new URI("/api/account-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-statuses} : Updates an existing accountStatus.
     *
     * @param accountStatusDTO the accountStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountStatusDTO,
     * or with status {@code 400 (Bad Request)} if the accountStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-statuses")
    public ResponseEntity<AccountStatusDTO> updateAccountStatus(@Valid @RequestBody AccountStatusDTO accountStatusDTO) throws URISyntaxException {
        log.debug("REST request to update AccountStatus : {}", accountStatusDTO);
        if (accountStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountStatusDTO result = accountStatusService.save(accountStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-statuses} : get all the accountStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountStatuses in body.
     */
    @GetMapping("/account-statuses")
    public List<AccountStatusDTO> getAllAccountStatuses() {
        log.debug("REST request to get all AccountStatuses");
        return accountStatusService.findAll();
    }

    /**
     * {@code GET  /account-statuses/:id} : get the "id" accountStatus.
     *
     * @param id the id of the accountStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-statuses/{id}")
    public ResponseEntity<AccountStatusDTO> getAccountStatus(@PathVariable Long id) {
        log.debug("REST request to get AccountStatus : {}", id);
        Optional<AccountStatusDTO> accountStatusDTO = accountStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountStatusDTO);
    }

    /**
     * {@code DELETE  /account-statuses/:id} : delete the "id" accountStatus.
     *
     * @param id the id of the accountStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-statuses/{id}")
    public ResponseEntity<Void> deleteAccountStatus(@PathVariable Long id) {
        log.debug("REST request to delete AccountStatus : {}", id);
        accountStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
